package by.tabolich.ecommerce.controller;

import by.tabolich.ecommerce.model.Order;
import by.tabolich.ecommerce.model.Status;
import by.tabolich.ecommerce.model.User;
import by.tabolich.ecommerce.repository.OrderRepository;
import by.tabolich.ecommerce.repository.StatusRepository;
import by.tabolich.ecommerce.repository.UserRepository;
import by.tabolich.ecommerce.services.OrderService;
import by.tabolich.ecommerce.services.UserService;
import com.sun.xml.bind.v2.TODO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class OrderController {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;

    @Autowired
    StatusRepository statusRepository;

    @GetMapping("order")
    public ResponseEntity<List<Order>> getAllOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        if (user == null || user.getRole().getId() != 1) {
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @GetMapping("order/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return ResponseEntity.ok().body(order);
    }

    @DeleteMapping("order/{id}")
    public ResponseEntity<Order> deleteOrderById(@PathVariable(value = "id") long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        User user = userService.getAuthorizationUser();
        if (user == null || !order.getUser().getId().equals(user.getId()) && user.getRole().getId() == 1) {
            logger.info("cant delete this order");
            return ResponseEntity.status(403).body(null);
        }
        orderRepository.delete(order);
        return ResponseEntity.ok().body(order);
    }

    @PatchMapping(path = "order/{id}/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> updateOrder(@RequestBody HashMap<String, String> request,
                                             @PathVariable(value = "id") long orderId) {
        try {
            userService.checkAuthorizationUserPermissions();
            Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
            Status status = statusRepository.findById(Long.parseLong(request.get("status_id"))).orElseThrow(() -> new ResourceNotFoundException("Status not found"));
            order.setStatus(status);
            order = orderRepository.save(order);
            logger.info("change status");
            orderService.sendMessageAboutOrder(order);
            return ResponseEntity.ok(order);
        } catch (AccessDeniedException exception) {
            return ResponseEntity.status(403).body(null);
        } catch (Exception exception) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
