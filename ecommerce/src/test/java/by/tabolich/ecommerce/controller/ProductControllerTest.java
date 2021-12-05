package by.tabolich.ecommerce.controller;

import by.tabolich.ecommerce.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductControllerTest {
    @Autowired
    ProductController productController;

    @Test
    public void contextLoads() {
        assertNotNull(productController);
    }

    @Test
    public void testAllProducts() {
        assertNotNull(productController.getAllProducts());
        assertNotEquals(productController.getAllProducts(), List.of());
    }
    @Test
    public void testDetailProduct() {
        assertNotNull(productController.getProductById(1));
        assertThrows(ResourceNotFoundException.class, () -> productController.getProductById(100));
    }
    @Test
    public void testProductVariants() {
        assertNotNull(productController.getProductVariantsById(1));
        assertThrows(ResourceNotFoundException.class, () -> productController.getProductVariantsById(100));
    }
}