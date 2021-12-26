package by.tabolich.ecommerce.controller;


import by.tabolich.ecommerce.config.jwt.JwtProvider;
import by.tabolich.ecommerce.model.Order;
import by.tabolich.ecommerce.services.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import by.tabolich.ecommerce.model.User;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class AuthController {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping(path = "register")
    public ResponseEntity<User> registerUser(@RequestBody HashMap<String, String> request) {
        User u = new User();
        u.setPassword(request.get("password"));
        u.setUsername(request.get("username"));
        u.setEmail(request.get("email"));
        try { userService.saveUser(u); }
        catch (Exception ex)
        { return new ResponseEntity<>(u, HttpStatus.BAD_REQUEST); }
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PostMapping("auth")
    public  ResponseEntity<Map<String, String>>  auth(@RequestBody HashMap<String, String> request) {
        Map<String, String> map = new HashMap<String, String>();
        User userEntity = userService.findByUsernameAndPassword(request.get("username"), request.get("password"));
        if (userEntity == null)
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);;
        String token = jwtProvider.generateToken(userEntity.getUsername());
        map.put("token", token);
        map.put("username", userEntity.getUsername());
        map.put("role", userEntity.getRole().getName());
        logger.info(map);
        return new ResponseEntity<>(map, HttpStatus.OK);

    }
}