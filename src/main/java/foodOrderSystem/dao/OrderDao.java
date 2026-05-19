package foodOrderSystem.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import foodOrderSystem.entity.Order;
import foodOrderSystem.repository.OrderRepository;
import foodOrderSystem.dto.OrderStatus;

@Repository
public class OrderDao {

    @Autowired
    private OrderRepository repository;

    public Order saveOrder(Order order) {
        return repository.save(order);
    }

    public Optional<Order> getOrderById(Integer id) {
        return repository.findById(id);
    }

    public List<Order> getAllOrders() {   
        return repository.findAll();
    }

    public List<Order> getOrdersByCustomer(Integer customreId) {
        return repository.findByCustomer_CustomreId(customreId);
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return repository.findByStatus(status);
    }

    public List<Order> getOrdersByDate(LocalDateTime start, LocalDateTime end) {
        return repository.findByOrderDateTimeBetween(start, end);
    }

    public List<Order> getOrdersByAmount(Double min, Double max) {
        return repository.findByTotalAmountBetween(min, max);
    }

    public List<Order> getOrdersByResturant(Integer resturantId) {
        return repository.findByOrderItem_MenuItem_Resturant_ResturantId(resturantId);
    }
    
    public Order updateOrderStatus(Order order) {
        return repository.save(order);
    }

    public Order cancelOrder(Order order) {
        return repository.save(order);
    }
}
