package by.tabolich.ecommerce.controller;


import by.tabolich.ecommerce.model.*;
import by.tabolich.ecommerce.repository.*;
import by.tabolich.ecommerce.services.CartService;
import by.tabolich.ecommerce.services.OrderService;
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
    CartService cartService;

    @Autowired
    UserService userService;

    @GetMapping(path = "cart")
    public ResponseEntity<Cart> showCart() {
        try {
            User user = userService.getAuthorizationUser();
            return ResponseEntity.ok(user.getCart());
        } catch (NullPointerException exception) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PostMapping(path = "cart", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> addCartDataToOrder() {
        User user = userService.getAuthorizationUser();
        Order order = cartService.addDataToOrder(user);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping(path = "cart/{id}/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductVariant> deleteCartItemFromOrder(@PathVariable(value = "id") long productVariantId) {
        User user = userService.getAuthorizationUser();
        ProductVariant productVariant = cartService.deleteCartItem(user, productVariantId);
        return new ResponseEntity<>(productVariant, HttpStatus.OK);
    }
}
