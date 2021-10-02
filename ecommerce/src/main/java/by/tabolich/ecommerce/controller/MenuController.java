package by.tabolich.ecommerce.controller;

import by.tabolich.ecommerce.model.Menu;
import by.tabolich.ecommerce.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class MenuController {
    @Autowired
    MenuRepository menuRepository;

    @GetMapping("menu")
    public List<Menu> getAllMenus() {
//        for (Menu menu: this.menuRepository.findAll()) {
//            System.out.println(menu.getTitle());
//
//        }
        return this.menuRepository.findAll();
    };

    @GetMapping("menu/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable(value = "id") long menuId) {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new ResourceNotFoundException("Menu not found"));
        return ResponseEntity.ok().body(menu);
    };
}
