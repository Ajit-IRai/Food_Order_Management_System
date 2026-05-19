package foodOrderSystem.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import foodOrderSystem.dao.CustomerDao;
import foodOrderSystem.dao.MenuItemDao;
import foodOrderSystem.dao.OrderDao;
import foodOrderSystem.dto.ResponseStructure;
import foodOrderSystem.dto.OrderStatus;
import foodOrderSystem.entity.Customer;
import foodOrderSystem.entity.MenuItem;
import foodOrderSystem.entity.Order;
import foodOrderSystem.entity.OrderItem;
import foodOrderSystem.exception.IdNotFoundException;
import foodOrderSystem.exception.ResourceNotFoundException;

@Service
public class OrderService {

    @Autowired
    private OrderDao dao;
    
    @Autowired
    private CustomerDao customerDao;
    
    @Autowired
    private MenuItemDao menuItemDao;

    //  Place Order
    public ResponseEntity<ResponseStructure<Order>> placeOrder(Order order) {

        if (order.getCustomer() == null || order.getPayment() == null) {
            throw new ResourceNotFoundException("Customer and Payment must be present");
        }
        Integer customerId = order.getCustomer().getCustomreId();

        Optional<Customer> optCustomer = customerDao.getCustomerById(customerId);

        Customer customer;

        if (optCustomer.isPresent()) {
            customer = optCustomer.get();
        } else {
            throw new ResourceNotFoundException("Customer not found");
        }

        order.setCustomer(customer);
 
        double total = 0;

        for (OrderItem item : order.getOrderItem()) {

            System.out.println(item.getMenuItem());
            System.out.println(item.getMenuItem().getItemId());

            Integer itemId = item.getMenuItem().getItemId();

            Optional<MenuItem> opt = menuItemDao.getMenuItemById(itemId);

            MenuItem menuItem;

            if (opt.isPresent()) {
                menuItem = opt.get();
            } else {
                throw new ResourceNotFoundException("Menu item not found");
            }

            if (!menuItem.getAvailability()) {
                throw new ResourceNotFoundException(
                    "Item not available: " + menuItem.getAvailability()
                );
            }   

            double price = menuItem.getPrice();
            double subTotal = price * item.getQuantity();

            item.setMenuItem(menuItem);
            item.setSubTotal(subTotal);
            item.setOrder(order);

            total += subTotal;
        }

     
        order.setTotalAmount(total);
        order.setOrderDateTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);

      
        order.getPayment().setOrder(order);
        order.getPayment().setAmount(total);

  
        Order saved = dao.saveOrder(order);

      
        ResponseStructure<Order> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.CREATED.value());
        structure.setMessage("Order placed successfully");
        structure.setData(saved);

        return new ResponseEntity<ResponseStructure<Order>>(structure, HttpStatus.CREATED);
    }

    //  Get All Orders
    public ResponseEntity<ResponseStructure<List<Order>>> getAllOrders() {

        List<Order> list = dao.getAllOrders();

        ResponseStructure<List<Order>> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Orders fetched");
        structure.setData(list);

        return new ResponseEntity<ResponseStructure<List<Order>>>(structure, HttpStatus.OK);
    }

    //Get By ID
    public ResponseEntity<ResponseStructure<Order>> getById(Integer id) {

        Optional<Order> opt = dao.getOrderById(id);

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Order not found");
        }

        ResponseStructure<Order> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Order found");
        structure.setData(opt.get());

        return new ResponseEntity<ResponseStructure<Order>>(structure, HttpStatus.OK);
    }

    //Get Orders of Customer
    public ResponseEntity<ResponseStructure<List<Order>>> getOrdersByCustomer(Integer customreId) {

        Optional<Customer> opt = customerDao.getCustomerById(customreId);

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Customer not found with id " + customreId);
        }

        List<Order> list = dao.getOrdersByCustomer(customreId);

        if (list.isEmpty()) {
            throw new IdNotFoundException("No orders found for customer id " + customreId);
        }

        ResponseStructure<List<Order>> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Customer orders fetched");
        structure.setData(list);

        return new ResponseEntity<ResponseStructure<List<Order>>>(structure, HttpStatus.OK);
    }

    //  Update Order Status
    public ResponseEntity<ResponseStructure<Order>> updateStatus(Integer id, OrderStatus status) {

        Optional<Order> opt = dao.getOrderById(id);

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Order not found");
        }

        Order order = opt.get();
        order.setStatus(status);

        Order updated = dao.updateOrderStatus(order);

        ResponseStructure<Order> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Order status updated");
        structure.setData(updated);

        return new ResponseEntity<ResponseStructure<Order>>(structure, HttpStatus.OK);
    }

    // Cancel Order
    public ResponseEntity<ResponseStructure<String>> cancelOrder(Integer id) {

        Optional<Order> opt = dao.getOrderById(id);

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Order not found");
        }

        Order order = opt.get();

        if (order.getStatus() == OrderStatus.PREPRARING ||order.getStatus()==OrderStatus.OUT_FOR_DELEVERY) {
            throw new ResourceNotFoundException("Order cannot be cancelled after preparation");
        }

        order.setStatus(OrderStatus.CANCELLED);

        dao.cancelOrder(order);

        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Order cancelled successfully");
        structure.setData("Cancelled");

        return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);
    }

    // Get By Status
    public ResponseEntity<ResponseStructure<List<Order>>> getByStatus(OrderStatus status) {

        List<Order> list = dao.getOrdersByStatus(status);

        ResponseStructure<List<Order>> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Orders by status fetched");
        structure.setData(list);

        return new ResponseEntity<ResponseStructure<List<Order>>>(structure, HttpStatus.OK);
    }

    //Get By Date
    public ResponseEntity<ResponseStructure<List<Order>>> getByDate(LocalDateTime start, LocalDateTime end) {

        List<Order> list = dao.getOrdersByDate(start, end);

        ResponseStructure<List<Order>> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Orders by date fetched");
        structure.setData(list);

        return new ResponseEntity<>(structure, HttpStatus.OK);
    }

    //  Get By Amount Range
    public ResponseEntity<ResponseStructure<List<Order>>> getByAmount(Double min, Double max) {

        List<Order> list = dao.getOrdersByAmount(min, max);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Order is not there between given range");
        }

        ResponseStructure<List<Order>> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Orders by amount range");
        structure.setData(list);

        return new ResponseEntity<ResponseStructure<List<Order>>>(structure, HttpStatus.OK);
    }

    // Orders of Restaurant
    public ResponseEntity<ResponseStructure<List<Order>>> getOrdersByResturant(Integer resturantId) {

        List<Order> list = dao.getOrdersByResturant(resturantId);

        if (list.isEmpty()) {
            throw new IdNotFoundException("No orders found for resturant id " + resturantId);
        }

        ResponseStructure<List<Order>> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Orders of restaurant fetched");
        structure.setData(list);

        return new ResponseEntity<ResponseStructure<List<Order>>>(structure, HttpStatus.OK);
    }
}