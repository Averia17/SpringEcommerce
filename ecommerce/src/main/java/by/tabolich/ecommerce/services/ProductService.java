package by.tabolich.ecommerce.services;

import by.tabolich.ecommerce.model.Cart;
import by.tabolich.ecommerce.model.Product;
import by.tabolich.ecommerce.model.ProductVariant;
import by.tabolich.ecommerce.repository.CartRepository;
import by.tabolich.ecommerce.repository.ProductRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import by.tabolich.ecommerce.model.User;

@Service
public class ProductService {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CartRepository cartRepository;

    public Cart addProductToCart(User user, long productId, String size)
    {
        Cart cart = user.getCart();
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        ProductVariant productVariant = product.getProduct_variants()
                .stream()
                .filter(x -> x.getSize().equals(size))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Product variant not found"));
        cart.getProductVariants().add(productVariant);
        cart = cartRepository.save(cart);
        return cart;
    }
    public Product createProduct(String title, Float price, String description, String image)
    {
        Product product = new Product();
        product.setTitle(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setImage("image");
        product = productRepository.save(product);
        logger.info("created new product");
        return product;
    }
}
