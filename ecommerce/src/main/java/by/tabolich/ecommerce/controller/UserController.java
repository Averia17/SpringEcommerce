package by.tabolich.ecommerce.controller;

import by.tabolich.ecommerce.model.User;
import by.tabolich.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
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
        User user = userRepository.findById(Long.parseLong("1")).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseEntity.ok().body(user);
    };

}
