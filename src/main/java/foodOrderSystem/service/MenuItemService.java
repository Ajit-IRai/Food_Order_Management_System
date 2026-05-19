package foodOrderSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import foodOrderSystem.dao.MenuItemDao;
import foodOrderSystem.dao.ResturantDao;
import foodOrderSystem.dto.ResponseStructure;
import foodOrderSystem.entity.MenuItem;
import foodOrderSystem.entity.Resturant;
import foodOrderSystem.exception.IdNotFoundException;
import foodOrderSystem.exception.ResourceNotFoundException;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemDao dao;
    
    @Autowired
    ResturantDao resturantDao;

    //  Add Menu Item
    public ResponseEntity<ResponseStructure<MenuItem>> addMenuItem(MenuItem item) {

        if (item.getPrice() < 0) {
            throw new DataIntegrityViolationException("Price cannot be negative");
        }
       Optional<Resturant> resturant=resturantDao.getResturantById(item.getResturant().getResturantId());
       
       if (item.getResturant() == null) {
            throw new ResourceNotFoundException("Restaurant must be present");
        }
  
        item.setResturant(resturant.get());;
        MenuItem saved = dao.saveMenuItem(item);

        ResponseStructure<MenuItem> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.CREATED.value());
        structure.setMessage("Menu item added successfully");
        structure.setData(saved);

        return new ResponseEntity<ResponseStructure<MenuItem>>(structure, HttpStatus.CREATED);
    }

    //  Get All
    public ResponseEntity<ResponseStructure<List<MenuItem>>> getAllMenuItems() {

        List<MenuItem> list = dao.getAllMenuItems();

        ResponseStructure<List<MenuItem>> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("All menu items fetched");
        structure.setData(list);

        return new ResponseEntity<ResponseStructure<List<MenuItem>>>(structure, HttpStatus.OK);
    }

    //  Get By ID
    public ResponseEntity<ResponseStructure<MenuItem>> getById(Integer id) {

        Optional<MenuItem> opt = dao.getMenuItemById(id);

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Menu item not found with id " + id);
        }

        ResponseStructure<MenuItem> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Menu item found");
        structure.setData(opt.get());

        return new ResponseEntity<ResponseStructure<MenuItem>>(structure, HttpStatus.OK);
    } 

    //  Update ONLY Price (PATCH)
    public ResponseEntity<ResponseStructure<MenuItem>> updatePrice(Integer id, Double price) {
  
        if (price < 0) {
            throw new ResourceNotFoundException("Price cannot be negative");
        }

        Optional<MenuItem> opt = dao.getMenuItemById(id);

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Menu item not found with id " + id);
        }

        MenuItem item = opt.get();
        item.setPrice(price);

        MenuItem updated = dao.updatePrice(item);

        ResponseStructure<MenuItem> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Price updated successfully");
        structure.setData(updated);

        return new ResponseEntity<ResponseStructure<MenuItem>>(structure, HttpStatus.OK);
    }  

    //  Delete
    public ResponseEntity<ResponseStructure<String>> deleteMenuItem(Integer id) {

        Optional<MenuItem> opt = dao.getMenuItemById(id);

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Item not found");
        }

        dao.deleteMenuItem(id);

        ResponseStructure<String> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Menu item deleted");
        structure.setData("Deleted successfully");

        return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.OK);
    }

    //  Filter by price
    public ResponseEntity<ResponseStructure<List<MenuItem>>> getItemsGreaterThan(Double price) {

        List<MenuItem> list = dao.getItemsGreaterThan(price);

        ResponseStructure<List<MenuItem>> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Items fetched with price greater than " + price);
        structure.setData(list);
  
        return new ResponseEntity<ResponseStructure<List<MenuItem>>>(structure, HttpStatus.OK);
    }

    //  Filter by name
    public ResponseEntity<ResponseStructure<List<MenuItem>>> getItemsByName(String name) {

        List<MenuItem> list = dao.getItemsByName(name);

        ResponseStructure<List<MenuItem>> structure = new ResponseStructure<>();
        structure.setStatusCode(HttpStatus.OK.value());
        structure.setMessage("Items fetched with name " + name);
        structure.setData(list);

        return new ResponseEntity<ResponseStructure<List<MenuItem>>>(structure, HttpStatus.OK);
    }
}
