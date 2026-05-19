package foodOrderSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import foodOrderSystem.dto.ResponseStructure;
import foodOrderSystem.dto.PaymentMethod;
import foodOrderSystem.dto.PaymentStatus;
import foodOrderSystem.entity.Payment;
import foodOrderSystem.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService service;

    //Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Payment>> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    //Get by Status
    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseStructure<List<Payment>>> getByStatus(@PathVariable PaymentStatus status) {
        return service.getByStatus(status);
    }

    //Get by Method
    @GetMapping("/method/{method}")
    public ResponseEntity<ResponseStructure<List<Payment>>> getByMethod(@PathVariable PaymentMethod method) {
        return service.getByMethod(method);
    }

    //Update Status
    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<ResponseStructure<Payment>> updateStatus(@PathVariable Long id,@PathVariable PaymentStatus status) {

        return service.updateStatus(id, status);
    }
}