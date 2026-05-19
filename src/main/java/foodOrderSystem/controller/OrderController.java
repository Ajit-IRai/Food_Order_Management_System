package foodOrderSystem.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import foodOrderSystem.dto.ResponseStructure;
import foodOrderSystem.dto.OrderStatus;
import foodOrderSystem.entity.Order;
import foodOrderSystem.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping
    public ResponseEntity<ResponseStructure<Order>> placeOrder(@RequestBody Order order) {
        return service.placeOrder(order);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Order>>> getAllOrders() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Order>> getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @GetMapping("/customer/{customreId}")
    public ResponseEntity<ResponseStructure<List<Order>>> getOrdersByCustomer(@PathVariable Integer customreId) {
        return service.getOrdersByCustomer(customreId);
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<ResponseStructure<Order>> updateStatus(@PathVariable Integer id,@PathVariable OrderStatus status) {
        return service.updateStatus(id, status);
    }
    
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ResponseStructure<String>> cancelOrder(@PathVariable Integer id) {
        return service.cancelOrder(id);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseStructure<List<Order>>> getByStatus(@PathVariable OrderStatus status) {
        return service.getByStatus(status);
    }

    @GetMapping("/date/{start}/{end}")
    public ResponseEntity<ResponseStructure<List<Order>>> getByDate(@PathVariable LocalDateTime start,@PathVariable LocalDateTime end) {
        return service.getByDate(start, end);
    }

    @GetMapping("/amount/{min}/{max}")
    public ResponseEntity<ResponseStructure<List<Order>>> getByAmount(@PathVariable Double min,@PathVariable Double max) {

        return service.getByAmount(min, max);
    }

    @GetMapping("/resturant/{resturantId}")
    public ResponseEntity<ResponseStructure<List<Order>>> getOrdersByResturant(@PathVariable Integer resturantId) {
        return service.getOrdersByResturant(resturantId);   
    }
}
