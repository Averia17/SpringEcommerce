package by.tabolich.ecommerce.controller;

import by.tabolich.ecommerce.model.User;
import by.tabolich.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("user")
    public ResponseEntity<User> getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getUserByUsername(authentication.getName());
        if (user == null) {
           return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok().body(user);
    };

}
