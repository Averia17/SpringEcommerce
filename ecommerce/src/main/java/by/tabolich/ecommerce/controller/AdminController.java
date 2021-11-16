package by.tabolich.ecommerce.controller;

import by.tabolich.ecommerce.model.Product;
import by.tabolich.ecommerce.model.ProductVariant;
import by.tabolich.ecommerce.repository.ProductRepository;
import by.tabolich.ecommerce.repository.ProductVariantRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    ProductRepository productRepository;

    @Autowired
    ProductVariantRepository productVariantRepository;

    @PostMapping(path = "product/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createNewProduct(@RequestBody HashMap<String, String> request) {
        Product product = new Product();
        try {
            product.setTitle(request.get("title"));
            product.setPrice(Float.parseFloat(request.get("price")));
            product.setDescription(request.get("description"));
            product.setImage(request.get("image"));
            product = productRepository.save(product);
            logger.info("created new product");
        } catch (NumberFormatException exception) {
            return ResponseEntity.status(403).body(product);
        }
        catch (Exception exception) {
            return ResponseEntity.status(400).body(product);
        }
        return  ResponseEntity.ok(product);
    }
    @PostMapping(path = "product/{id}/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProductVariant(@RequestBody HashMap<String, String> request,
                                                    @PathVariable(value = "id") long productId) {
        ProductVariant productVariant = new ProductVariant();
        Product product = new Product();
        try {
            product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            String size = request.get("size");
            productVariant.setSize(size);
            productVariant.setProduct(product);
            if (product.getProduct_variants().stream().anyMatch(x -> x.getSize().equals(size)))
                return ResponseEntity.status(406).body(product);
            productVariant = productVariantRepository.save(productVariant);
            logger.info("created new product variant");
        }

        catch (Exception exception) {
            return ResponseEntity.status(400).body(product);
        }

        return  ResponseEntity.ok(product);
    }
    // NEED TEST
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @DeleteMapping(path = "product_variant/{id}/")
    public ResponseEntity<Product> deleteProductVariant(@PathVariable(value = "id") long productVariantId) {
        Product product = new Product();
        try {

            productVariantRepository.deleteById(productVariantId);
            ProductVariant productVariant = productVariantRepository.getById(productVariantId);
            System.out.println(productVariant.getId());
            logger.info("deleted product variant");
        }

        catch (Exception exception) {
            return ResponseEntity.status(400).body(product);
        }

        return  ResponseEntity.ok(product);
    }
    // NEED TEST
    @PatchMapping(path = "product/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@RequestBody HashMap<String, String> request,
                                                 @PathVariable(value = "id") long productId) {
        Product product = new Product();
        try {
            product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
            product.setTitle(request.get("title"));
            product.setPrice(Float.parseFloat(request.get("price")));
            product.setDescription(request.get("description"));
            product.setImage(request.get("image"));
            product = productRepository.save(product);
            logger.info("updated product");

        } catch (NumberFormatException exception) {
            return ResponseEntity.status(403).body(product);
        }
        catch (Exception exception) {
            return ResponseEntity.status(400).body(product);
        }
        return  ResponseEntity.ok(product);
    }
}
