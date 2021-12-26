package by.tabolich.ecommerce.controller;

import by.tabolich.ecommerce.model.Order;
import by.tabolich.ecommerce.model.Status;
import by.tabolich.ecommerce.model.User;
import by.tabolich.ecommerce.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class StatusController {
    @Autowired
    StatusRepository statusRepository;
    @GetMapping("statuses")
    public ResponseEntity<List<Status>> getAllStatuses() {
        return  ResponseEntity.ok(statusRepository.findAll());
    };
}
