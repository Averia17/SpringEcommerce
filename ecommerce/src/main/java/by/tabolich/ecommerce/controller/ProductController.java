package by.tabolich.ecommerce.controller;


import by.tabolich.ecommerce.model.Cart;
import by.tabolich.ecommerce.model.Product;
import by.tabolich.ecommerce.model.ProductVariant;
import by.tabolich.ecommerce.model.User;
import by.tabolich.ecommerce.repository.CartRepository;
import by.tabolich.ecommerce.repository.ProductRepository;
import by.tabolich.ecommerce.repository.UserRepository;
import by.tabolich.ecommerce.services.ProductService;
import by.tabolich.ecommerce.services.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class ProductController {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @GetMapping("product")
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    };

    @GetMapping("products/")
    public ResponseEntity<List<Product>> getProductsByGender(@RequestParam String gender) {
        return ResponseEntity.ok().body(productRepository.findProductsByGender(gender));
    };
    @GetMapping("filter/products/")
    public ResponseEntity<List<Product>> searchProducts (@RequestParam String search) {
        List<Product> filteredProducts = productRepository.findAll().stream().filter(o -> o.getTitle().contains(search)).collect(Collectors.toList());
        return ResponseEntity.ok().body(filteredProducts);
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
        User user = userService.getAuthorizationUser();
        Cart cart = productService.addProductToCart(user, productId, size);
        return ResponseEntity.ok().body(cart);

    };
    @DeleteMapping(path = "product/{id}/")
    public ResponseEntity<Product> deleteProduct(@PathVariable(value = "id") long productId) {
        Product product = new Product();
        try {
            userService.checkAuthorizationUserPermissions();
            product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            productRepository.delete(product);
            logger.info("delete product");
        }
        catch (AccessDeniedException exception) {
            return ResponseEntity.status(403).body(null);
        }
        catch (Exception exception) {
            return ResponseEntity.status(400).body(product);
        }
        return  ResponseEntity.ok(product);
    }


}
