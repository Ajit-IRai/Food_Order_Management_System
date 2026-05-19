package foodOrderSystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import foodOrderSystem.dto.ResponseStructure;
import foodOrderSystem.entity.Customer;
import foodOrderSystem.entity.Resturant;
import foodOrderSystem.repository.CustomerRepository;
import foodOrderSystem.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
 
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired 
	CustomerService customerService;
	
	@PostMapping
	public ResponseEntity<ResponseStructure<Customer>> saveCustomer(@RequestBody Customer customer){
		return customerService.saveCustomer(customer);  
	}
	
	@GetMapping("/customers")
	public ResponseEntity<ResponseStructure<List<Customer>>> getAllCustomer(){
		return customerService.getAllCustomer();  
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Optional<Customer>>> getCustomerById(@PathVariable Integer id){
		return customerService.getCustomerById(id);
	}     
	
	@PutMapping("/api")
	public ResponseEntity<ResponseStructure<Customer>> updateCustomer(@RequestBody Customer customer){
		return customerService.updateCustomer(customer); 
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteCustomer(@PathVariable Integer id){
		return customerService.deleteCustomer(id);  
	}
	
	@GetMapping("/contact/{contactNo}")
	public ResponseEntity<ResponseStructure<Customer>> getCustomerByContact(@PathVariable String contactNo){
		return customerService.getCustomerByContact(contactNo);   
	}
	
}
