package by.tabolich.ecommerce.controller;


import by.tabolich.ecommerce.config.jwt.JwtProvider;
import by.tabolich.ecommerce.model.Order;
import by.tabolich.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import by.tabolich.ecommerce.model.User;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping(path = "register")
    public ResponseEntity<User> registerUser(@RequestBody HashMap<String, String> request) {
        User u = new User();
        u.setPassword(request.get("password"));
        u.setUsername(request.get("username"));
        try { userService.saveUser(u); }
        catch (Exception ex)
        { return new ResponseEntity<>(u, HttpStatus.BAD_REQUEST); }
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PostMapping("auth")
    public  Map<String, String>  auth(@RequestBody HashMap<String, String> request) {
        User userEntity = userService.findByUsernameAndPassword(request.get("username"), request.get("password"));
        String token = jwtProvider.generateToken(userEntity.getUsername());
        Map<String, String> map = new HashMap<String, String>();
        map.put("token", token);
        return map;
    }
}