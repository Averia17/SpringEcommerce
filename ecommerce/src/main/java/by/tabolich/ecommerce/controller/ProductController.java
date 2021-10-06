package by.tabolich.ecommerce.controller;


import by.tabolich.ecommerce.dao.CartDao;
import by.tabolich.ecommerce.dao.CartItemDao;
import by.tabolich.ecommerce.model.Cart;
import by.tabolich.ecommerce.model.Product;
import by.tabolich.ecommerce.model.ProductVariant;
import by.tabolich.ecommerce.model.User;
import by.tabolich.ecommerce.repository.CartRepository;
import by.tabolich.ecommerce.repository.ProductRepository;
import by.tabolich.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartItemDao cartItemDao;

    @Autowired
    CartDao cartDao;

    @GetMapping("product")
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    };

    @GetMapping("product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("product/{id}/variants")
    public  ResponseEntity<List<ProductVariant>> getProductVariantsById(@PathVariable(value = "id") long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        List<ProductVariant> productVariants = product.getProduct_variants();
        return ResponseEntity.ok().body(productVariants);
    }

    @GetMapping(path = "product/{id}/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cart> addProductToCart(@PathVariable(value = "id") long productId, @RequestParam String size) {
        System.out.println(size);
        User user = userRepository.getById(Long.parseLong("1"));
        System.out.println(user.getUsername());
        Cart cart = user.getCart();
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        ProductVariant productVariant = product.getProduct_variants()
                .stream()
                .filter(x -> x.getSize().equals(size))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Product variant not found"));
        cart.getProductVariants().add(productVariant);
        cart = cartRepository.save(cart);
        return ResponseEntity.ok().body(cart);

    };



}
