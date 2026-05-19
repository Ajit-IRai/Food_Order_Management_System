package foodOrderSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import foodOrderSystem.entity.Customer;
import foodOrderSystem.entity.MenuItem;
import foodOrderSystem.entity.Resturant;

public interface ResturantRepository extends JpaRepository<Resturant, Integer>{
	
	Optional<Resturant> findBylocation(String location);
	
	Optional<Resturant> findByresturantName(String resturantName);
	
//	List<MenuItem> findByResturant_ResturantId(int resturantId);
  
}
