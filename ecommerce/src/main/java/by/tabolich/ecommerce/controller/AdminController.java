package by.tabolich.ecommerce.controller;

import by.tabolich.ecommerce.model.Order;
import by.tabolich.ecommerce.model.Product;
import by.tabolich.ecommerce.model.ProductVariant;
import by.tabolich.ecommerce.model.Status;
import by.tabolich.ecommerce.repository.OrderRepository;
import by.tabolich.ecommerce.repository.ProductRepository;
import by.tabolich.ecommerce.repository.ProductVariantRepository;
import by.tabolich.ecommerce.repository.StatusRepository;
import by.tabolich.ecommerce.services.ProductService;
import by.tabolich.ecommerce.services.ProductVariantService;
import by.tabolich.ecommerce.services.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {
    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductVariantService productVariantService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductVariantRepository productVariantRepository;

    @PostMapping(path = "product/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createNewProduct(@RequestBody HashMap<String, String> request) {
        Product product = new Product();
        try {
            userService.checkAuthorizationUserPermissions();
            product = productService.createProduct(
                    request.get("title"),
                    Float.parseFloat(request.get("price")),
                    request.get("description"),
                    request.get("image"));
            logger.info("created new product");
        } catch (AccessDeniedException exception) {
            return ResponseEntity.status(403).body(null);
        } catch (Exception exception) {
            return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping(path = "product/{id}/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProductVariant(@RequestBody HashMap<String, String> request,
                                                        @PathVariable(value = "id") long productId) {
        ProductVariant productVariant = new ProductVariant();
        Product product = new Product();
        try {
            userService.checkAuthorizationUserPermissions();
            product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            String size = request.get("size");
            if (product.getProduct_variants().stream().anyMatch(x -> x.getSize().equals(size)))
                return ResponseEntity.status(400).body(product);
            productVariantService.createProductVariant(product, size);
        } catch (AccessDeniedException exception) {
            return ResponseEntity.status(403).body(product);
        } catch (Exception exception) {
            return ResponseEntity.status(400).body(product);
        }

        return ResponseEntity.ok(product);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @DeleteMapping(path = "product_variant/{id}/")
    public ResponseEntity<Product> deleteProductVariant(@PathVariable(value = "id") long productVariantId) {
        Product product = new Product();
        try {
            userService.checkAuthorizationUserPermissions();
            productVariantRepository.deleteById(productVariantId);
            logger.info("deleted product variant");
        } catch (AccessDeniedException exception) {
            return ResponseEntity.status(403).body(product);
        } catch (Exception exception) {
            return ResponseEntity.status(400).body(product);
        }
        return ResponseEntity.ok(product);
    }

    @PatchMapping(path = "product/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@RequestBody HashMap<String, String> request,
                                                 @PathVariable(value = "id") long productId) {
        Product product = new Product();
        try {
            userService.checkAuthorizationUserPermissions();
            product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            product.setTitle(request.get("title"));
            product.setPrice(Float.parseFloat(request.get("price")));
            product.setDescription(request.get("description"));
            product.setImage(request.get("image"));
            product = productRepository.save(product);

        } catch (AccessDeniedException exception) {
            return ResponseEntity.status(403).body(product);
        } catch (Exception exception) {
            return ResponseEntity.status(400).body(product);
        }
        return ResponseEntity.ok(product);
    }
}
