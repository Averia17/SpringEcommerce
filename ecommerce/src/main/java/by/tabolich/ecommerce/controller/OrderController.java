package by.tabolich.ecommerce.controller;

import by.tabolich.ecommerce.model.Order;
import by.tabolich.ecommerce.repository.OrderRepository;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @GetMapping("order")
    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    };

    @GetMapping("order/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return ResponseEntity.ok().body(order);
    };
    @DeleteMapping("order/{id}")
    public ResponseEntity<Order> deleteOrderById(@PathVariable(value = "id") long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        orderRepository.delete(order);
        return ResponseEntity.ok().body(order);
    };
}
