package foodOrderSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import foodOrderSystem.dto.PaymentMethod;
import foodOrderSystem.dto.PaymentStatus;
import foodOrderSystem.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

	List<Payment> findByPaymentStatus(PaymentStatus status);

    List<Payment> findByPaymnetMethod(PaymentMethod method);
}
