package by.tabolich.ecommerce.services;

import by.tabolich.ecommerce.model.Product;
import by.tabolich.ecommerce.model.ProductVariant;
import by.tabolich.ecommerce.repository.ProductVariantRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductVariantService {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    ProductVariantRepository productVariantRepository;

    public ProductVariant createProductVariant(Product product, String size){
        ProductVariant productVariant = new ProductVariant();
        productVariant.setSize(size);
        productVariant.setProduct(product);
        productVariant = productVariantRepository.save(productVariant);
        logger.info("created new product variant");
        return productVariant;
    }
}
