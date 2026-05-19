package foodOrderSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import foodOrderSystem.dto.ResponseStructure;
import foodOrderSystem.entity.OrderItem;
import foodOrderSystem.service.OrderItemService;

@RestController
@RequestMapping("/orderItem")
public class OrderItemController {

    @Autowired
    private OrderItemService service;

    // Add item to order
    @PostMapping("/{orderId}")
    public ResponseEntity<ResponseStructure<OrderItem>> addItem(@PathVariable Integer orderId,@RequestBody OrderItem item) {

        return service.addItem(orderId, item);
    }

    //Update quantity
    @PatchMapping("/{id}/{quantity}")
    public ResponseEntity<ResponseStructure<OrderItem>> updateQuantity(@PathVariable Integer id,@PathVariable Integer quantity) {

        return service.updateQuantity(id, quantity);
    }
   
    // Remove item
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> removeItem(@PathVariable Integer id) {
        return service.removeItem(id);
    }

    // Get items of order
    @GetMapping("/order/{orderId}")
    public ResponseEntity<ResponseStructure<List<OrderItem>>> getItemsByOrder(@PathVariable Integer orderId) {

        return service.getItemsByOrder(orderId);
    }
}