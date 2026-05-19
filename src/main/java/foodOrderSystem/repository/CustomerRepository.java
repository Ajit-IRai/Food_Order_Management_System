package foodOrderSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import foodOrderSystem.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	 boolean existsByEmail(String email);

	 boolean existsByContactNo(String contactNo);
	 
	 Optional<Customer> findByContactNo(String contactNo);
}
