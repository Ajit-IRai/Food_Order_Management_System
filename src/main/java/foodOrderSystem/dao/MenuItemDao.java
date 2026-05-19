package foodOrderSystem.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import foodOrderSystem.entity.MenuItem;
import foodOrderSystem.repository.MenuItemRepository;

@Repository
public class MenuItemDao {

    @Autowired
    private MenuItemRepository repository;

    public MenuItem saveMenuItem(MenuItem item) {
        return repository.save(item);
    }

    public Optional<MenuItem> getMenuItemById(Integer id) {
        return repository.findById(id);
    }

    public List<MenuItem> getAllMenuItems() {
        return repository.findAll();  
    }

    public void deleteMenuItem(Integer id) {
        repository.deleteById(id);
    }

    public List<MenuItem> getItemsGreaterThan(Double price) {
        return repository.findByPriceGreaterThan(price);
    }

    public List<MenuItem> getItemsByName(String name) {
        return repository.findByItemName(name);
    }

    // ✅ Update price
    public MenuItem updatePrice(MenuItem item) {
        return repository.save(item);
    }
}