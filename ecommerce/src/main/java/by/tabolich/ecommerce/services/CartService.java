package by.tabolich.ecommerce.services;

import by.tabolich.ecommerce.model.Cart;
import by.tabolich.ecommerce.model.Order;
import by.tabolich.ecommerce.model.ProductVariant;
import by.tabolich.ecommerce.model.Status;
import by.tabolich.ecommerce.repository.CartRepository;
import by.tabolich.ecommerce.repository.OrderRepository;
import by.tabolich.ecommerce.repository.ProductVariantRepository;
import by.tabolich.ecommerce.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import by.tabolich.ecommerce.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductVariantRepository productVariantRepository;

    public Order addDataToOrder(User user) {
        Cart cart = user.getCart();
        List<ProductVariant> productVariants = cart.getProductVariants();
        Status status = statusRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Status not found"));
        Order order = new Order(user, status);
        order = orderRepository.save(order);
        order.getProductVariants().addAll(productVariants);
        order = orderRepository.save(order);
        return order;
    }
    public ProductVariant deleteCartItem(User user, long productVariantId) {
        Cart cart = user.getCart();
        List<ProductVariant> productVariants = cart.getProductVariants();
        ProductVariant productVariant = productVariantRepository.findById(productVariantId).orElseThrow(() -> new ResourceNotFoundException("Product variant not found"));
        System.out.println(productVariant.getId());
        productVariants.remove(productVariant);
        cart.setProductVariants(productVariants);
        cartRepository.save(cart);
        return productVariant;
    }
}
