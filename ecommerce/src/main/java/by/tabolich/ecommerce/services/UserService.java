package by.tabolich.ecommerce.services;

import by.tabolich.ecommerce.model.Cart;
import by.tabolich.ecommerce.model.RoleEntity;
import by.tabolich.ecommerce.model.User;

import by.tabolich.ecommerce.repository.CartRepository;
import by.tabolich.ecommerce.repository.RoleEntityRepository;
import by.tabolich.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleEntityRepository roleEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CartRepository cartRepository;

    public User saveUser(User user) {
        if(userRepository.getUserByUsername(user.getUsername()) != null)
            throw new EntityExistsException("User with this username already exists");
        RoleEntity userRole = roleEntityRepository.getRoleEntityByName("ROLE_USER");
        user.setRole(userRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Cart cart = new Cart();
        cart = cartRepository.save(cart);
        user.setCart(cart);
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public User findByUsernameAndPassword(String username, String password) {
        User userEntity = findByUsername(username);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }
    public User getAuthorizationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.getUserByUsername(authentication.getName());
    }
    public void checkAuthorizationUserPermissions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        System.out.println(user.getUsername());
        System.out.println(getAuthorizationUser());
        System.out.println(getAuthorizationUser().getRole().getId());
        if (getAuthorizationUser() == null || getAuthorizationUser().getRole().getId() != 1) {
            throw new AccessDeniedException("Access only for admins");
        }
    }
}