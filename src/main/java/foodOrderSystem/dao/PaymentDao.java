package foodOrderSystem.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import foodOrderSystem.entity.Payment;
import foodOrderSystem.repository.PaymentRepository;
import foodOrderSystem.dto.PaymentMethod;
import foodOrderSystem.dto.PaymentStatus;

@Repository
public class PaymentDao {

    @Autowired
    private PaymentRepository repository;

    public Optional<Payment> getPaymentById(Long id) {
        return repository.findById(id);  
    }

    public List<Payment> getByStatus(PaymentStatus status) {
        return repository.findByPaymentStatus(status);
    }

    public List<Payment> getByMethod(PaymentMethod method) {
        return repository.findByPaymnetMethod(method);
    }

    public Payment updatePayment(Payment payment) {
        return repository.save(payment);
    }
}
