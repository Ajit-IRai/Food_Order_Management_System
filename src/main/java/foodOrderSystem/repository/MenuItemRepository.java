package foodOrderSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import foodOrderSystem.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer>{

	
	List<MenuItem> findByPriceGreaterThan(Double price);

    List<MenuItem> findByItemName(String itemName);
}
