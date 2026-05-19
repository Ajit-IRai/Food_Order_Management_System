package foodOrderSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import foodOrderSystem.dto.ResponseStructure;
import foodOrderSystem.entity.MenuItem;
import foodOrderSystem.service.MenuItemService;

@RestController
@RequestMapping("/menu")
public class MenuItemController {

    @Autowired
    private MenuItemService service;

    @PostMapping
    public ResponseEntity<ResponseStructure<MenuItem>> addMenuItem(@RequestBody MenuItem item) {
        return service.addMenuItem(item);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<MenuItem>>> getAllMenuItems() {
        return service.getAllMenuItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<MenuItem>> getById(@PathVariable 	Integer id) {
        return service.getById(id);
    }

    
    @PatchMapping("/{id}/{price}")
    public ResponseEntity<ResponseStructure<MenuItem>> updatePrice(@PathVariable Integer id,@PathVariable Double price) {
        return service.updatePrice(id, price);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteMenuItem(@PathVariable Integer id) {
        return service.deleteMenuItem(id);
    }

    @GetMapping("/greater/{price}")
    public ResponseEntity<ResponseStructure<List<MenuItem>>> getItemsGreaterThan(@PathVariable Double price) {
        return service.getItemsGreaterThan(price);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseStructure<List<MenuItem>>> getItemsByName(@PathVariable String name) {
        return service.getItemsByName(name);
    }
}