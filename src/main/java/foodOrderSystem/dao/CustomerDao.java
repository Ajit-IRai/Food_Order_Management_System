package foodOrderSystem.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import foodOrderSystem.entity.Customer;
import foodOrderSystem.entity.Resturant;
import foodOrderSystem.repository.CustomerRepository;
import foodOrderSystem.repository.ResturantRepository;

@Repository
public class CustomerDao {

	@Autowired
	CustomerRepository customerRepository;
	 
	/*
	 * add the customer
	 */
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer); 
	}
	
	/*
	 * get all customers
	 */
	public List<Customer> getAllCustomer(){
		return customerRepository.findAll();    
	}
	
	/*
	 * get by ID
	 */
	public Optional<Customer> getCustomerById(Integer id){
		return customerRepository.findById(id); 
	}
	
	/*
	 * update the customer
	 */
	public Customer updateCustomer(Customer customer) {
		return customerRepository.save(customer);  
	}  
	/*
	 * delete the customer
	 */
	public String deleteCustomer(Integer id) {
		return "The custome is deleted by id "+id;  
	}
	
	/*
	 * find by contact no.
	 */
	public Optional<Customer> getCustomerByContact(String contactNo){
		return customerRepository.findByContactNo(contactNo); 
	}
	
}
