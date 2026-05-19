package foodOrderSystem.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import foodOrderSystem.entity.OrderItem;
import foodOrderSystem.repository.OrderItemRepository;

@Repository
public class OrderItemDao {

    @Autowired
    private OrderItemRepository repository;

    public OrderItem saveOrderItem(OrderItem item) {
        return repository.save(item);
    }
  
    public Optional<OrderItem> getById(Integer id) {
        return repository.findById(id);
    }

    public void deleteOrderItem(Integer id) {
        repository.deleteById(id);
    }

    public List<OrderItem> getByOrderId(Integer orderId) {
        return repository.findByOrder_OrderId(orderId);
    }
}