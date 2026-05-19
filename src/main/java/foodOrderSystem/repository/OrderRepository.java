package foodOrderSystem.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import foodOrderSystem.dto.OrderStatus;
import foodOrderSystem.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

	
		List<Order> findByCustomer_CustomreId(Integer customreId);

	    List<Order> findByStatus(OrderStatus status);
  
	    List<Order> findByOrderDateTimeBetween(LocalDateTime start, LocalDateTime end);

	    List<Order> findByTotalAmountBetween(Double min, Double max);

	    List<Order> findByOrderItem_MenuItem_Resturant_ResturantId(Integer resturantId);
}
