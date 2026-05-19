package foodOrderSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import foodOrderSystem.dao.MenuItemDao;
import foodOrderSystem.dao.OrderDao;
import foodOrderSystem.dao.OrderItemDao;
import foodOrderSystem.dto.ResponseStructure;
import foodOrderSystem.entity.MenuItem;
import foodOrderSystem.entity.Order;
import foodOrderSystem.entity.OrderItem;
import foodOrderSystem.exception.IdNotFoundException;
import foodOrderSystem.exception.ResourceNotFoundException;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao dao;

    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    MenuItemDao menuItemDao;

    // Add item to existing order
    public ResponseEntity<ResponseStructure<OrderItem>> addItem(int orderId, OrderItem item) {

        Optional<Order> opt = orderDao.getOrderById(orderId);

        if (opt.isEmpty()) {
            throw new ResourceNotFoundException("Order not found");
        }

        if (item.getMenuItem() == null || item.getMenuItem().getItemId() == null) {
            throw new ResourceNotFoundException("MenuItem ID must be provided");
        }

        Integer itemId = item.getMenuItem().getItemId();

        Optional<MenuItem> optMenu = menuItemDao.getMenuItemById(itemId);

        MenuItem menuItem;

        if (optMenu.isPresent()) {
            menuItem = optMenu.get();
        } else {
            throw new ResourceNotFoundException("Menu item not found");
        }

        if (menuItem.getPrice() == null) {
            throw new ResourceNotFoundException("Menu item price is null in DB");
        }

        double subTotal = menuItem.getPrice() * item.getQuantity();

        item.setMenuItem(menuItem);   
        item.setSubTotal(subTotal);
        item.setOrder(opt.get());

        OrderItem saved = dao.saveOrderItem(item);

        ResponseStructure<OrderItem> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.CREATED.value());
        structure.setMessage("Item added to order");
        structure.setData(saved);

        return new ResponseEntity<ResponseStructure<OrderItem>>(structure, HttpStatus.CREATED);
    }

    // Update quantity
    public ResponseEntity<ResponseStructure<OrderItem>> updateQuantity(Integer id, Integer quantity) {

        Optional<OrderItem> opt = dao.getById(id);

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Order item not found");
        }

        if (quantity < 1) {
            throw new ResourceNotFoundException("Quantity must be at least 1");
        }
   
        OrderItem item = opt.get();

        item.setQuantity(quantity);

        double subTotal = item.getMenuItem().getPrice() * quantity;
        item.setSubTotal(subTotal);

        OrderItem updated = dao.saveOrderItem(item);

        ResponseStructure<OrderItem> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Quantity updated");
        structure.setData(updated);

        return new ResponseEntity<ResponseStructure<OrderItem>>(structure, HttpStatus.OK);
    }

    // Remove item
    public ResponseEntity<ResponseStructure<String>> removeItem(Integer id) {

        Optional<OrderItem> opt = dao.getById(id);

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Order item not found");
        }

        dao.deleteOrderItem(id);

        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Item removed from order");
        structure.setData("Deleted");

        return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);
    }

    //Get items of an order
    public ResponseEntity<ResponseStructure<List<OrderItem>>> getItemsByOrder(Integer orderId) {

        List<OrderItem> list = dao.getByOrderId(orderId);

        if (list.isEmpty()) {
            throw new IdNotFoundException("No items found for this order");
        }

        ResponseStructure<List<OrderItem>> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Order items fetched");
        structure.setData(list);

        return new ResponseEntity<ResponseStructure<List<OrderItem>>>(structure, HttpStatus.OK);
    }
}