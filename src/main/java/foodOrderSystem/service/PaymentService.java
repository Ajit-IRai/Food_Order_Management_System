package foodOrderSystem.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.ResourceClosedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import foodOrderSystem.dao.PaymentDao;
import foodOrderSystem.dto.ResponseStructure;
import foodOrderSystem.dto.PaymentMethod;
import foodOrderSystem.dto.PaymentStatus;
import foodOrderSystem.entity.Payment;
import foodOrderSystem.exception.IdNotFoundException;
import foodOrderSystem.exception.ResourceNotFoundException;

@Service
public class PaymentService {

    @Autowired
    private PaymentDao dao;

    // Get by ID
    public ResponseEntity<ResponseStructure<Payment>> getById(Long id) {

        Optional<Payment> opt = dao.getPaymentById(id);

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Payment not found");
        }

        ResponseStructure<Payment> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Payment found");
        structure.setData(opt.get());

        return new ResponseEntity<ResponseStructure<Payment>>(structure, HttpStatus.OK);
    }

    //Get by Status
    public ResponseEntity<ResponseStructure<List<Payment>>> getByStatus(PaymentStatus status) {

        List<Payment> list = dao.getByStatus(status);
        
        if(list.isEmpty()) {
        	throw new ResourceNotFoundException("There is no such payment exist");
        }
        ResponseStructure<List<Payment>> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Payments fetched by status");
        structure.setData(list);

        return new ResponseEntity<ResponseStructure<List<Payment>>>(structure, HttpStatus.OK);
    }

    // Get by Payment Method
    public ResponseEntity<ResponseStructure<List<Payment>>> getByMethod(PaymentMethod method) {

        List<Payment> list = dao.getByMethod(method);
        
        if(list.isEmpty()) {
        	throw new ResourceNotFoundException("There is no such paymentMethod exist");
        }

        ResponseStructure<List<Payment>> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Payments fetched by method");
        structure.setData(list);

        return new ResponseEntity<ResponseStructure<List<Payment>>>(structure, HttpStatus.OK);
    }

    //Update Payment Status 
    public ResponseEntity<ResponseStructure<Payment>> updateStatus(Long id, PaymentStatus status) {

        Optional<Payment> opt = dao.getPaymentById(id);

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Payment not found");
        }

        Payment payment = opt.get();

        if (!payment.getAmount().equals(payment.getOrder().getTotalAmount())) {
            throw new RuntimeException("Payment amount must match order total amount");
        }

        payment.setPaymentStatus(status);

        Payment updated = dao.updatePayment(payment);

        ResponseStructure<Payment> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Payment status updated");
        structure.setData(updated);

        return new ResponseEntity<ResponseStructure<Payment>>(structure, HttpStatus.OK);
    }
}