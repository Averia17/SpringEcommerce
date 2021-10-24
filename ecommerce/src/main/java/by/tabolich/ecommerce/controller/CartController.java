package by.tabolich.ecommerce.controller;


import by.tabolich.ecommerce.dao.OrderDao;
import by.tabolich.ecommerce.dao.ProductVariantDao;
import by.tabolich.ecommerce.dao.UserDao;
import by.tabolich.ecommerce.model.Cart;
import by.tabolich.ecommerce.model.Order;
import by.tabolich.ecommerce.model.ProductVariant;
import by.tabolich.ecommerce.model.User;
import by.tabolich.ecommerce.repository.CartRepository;
import by.tabolich.ecommerce.repository.OrderRepository;
import by.tabolich.ecommerce.repository.UserRepository;
import by.tabolich.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CartController {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    OrderDao orderDao;

    @Autowired
    ProductVariantDao productVariantDao;

    @GetMapping(path = "cart")
    public ResponseEntity<Cart> showCart(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        return ResponseEntity.ok(user.getCart());
    }

    @PostMapping(path = "cart", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> addCartDataToOrder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        Cart cart = user.getCart();
        List<ProductVariant> productVariants = cart.getProductVariants();
        Order order = new Order(user);
        order = orderRepository.save(order);
        order.getProductVariants().addAll(productVariants);
        order = orderRepository.save(order);

        System.out.println(order.getId());

        return new ResponseEntity<>(order, HttpStatus.OK);
    };
    @DeleteMapping(path = "cart/{id}/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductVariant> deleteCartItemFromOrder(@PathVariable(value = "id") long productVariantId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        Cart cart = user.getCart();
        // TODO: remove to service this bad code
        List<ProductVariant> productVariants = cart.getProductVariants();
        ProductVariant productVariant = productVariantDao.findById(productVariantId).orElseThrow(() -> new ResourceNotFoundException("Product variant not found"));
        System.out.println(productVariant.getId());
        productVariants.remove(productVariant);
        cart.setProductVariants(productVariants);
        cartRepository.save(cart);
        //

        return new ResponseEntity<>(productVariant, HttpStatus.OK);
    };
}
