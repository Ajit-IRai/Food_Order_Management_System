package foodOrderSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import foodOrderSystem.dao.CustomerDao;
import foodOrderSystem.dto.ResponseStructure;
import foodOrderSystem.entity.Customer;
import foodOrderSystem.entity.Resturant;
import foodOrderSystem.exception.IdNotFoundException;
import foodOrderSystem.exception.NoRecordFoundException;
import foodOrderSystem.exception.ResourceNotFoundException;
import foodOrderSystem.repository.CustomerRepository;
import foodOrderSystem.repository.ResturantRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	CustomerRepository customerRepository;

	   
	/*
	 * add the customer
	 */
	public ResponseEntity<ResponseStructure<Customer>> saveCustomer(Customer customer){
		ResponseStructure<Customer> response=new ResponseStructure<Customer>();
		if(customerRepository.existsByEmail(customer.getEmail())) {
		throw new ResourceNotFoundException("The email is already existing in the DB");
		}
		if(String.valueOf(customer.getContactNo()).length()!=10) {
		throw new ResourceNotFoundException("The contact is already existing in the DB");
			
		}    
		 response.setStatusCode(HttpStatus.OK.value());
		 response.setMessage("Customer successfully added into DB");
		 response.setData(customerDao.saveCustomer(customer));
		 return new ResponseEntity<ResponseStructure<Customer>>(response,HttpStatus.OK);  
	}
	
	/*
	 * get all customers
	 */
	public ResponseEntity<ResponseStructure<List<Customer>>> getAllCustomer(){
		List<Customer> customers=customerDao.getAllCustomer();
		ResponseStructure<List<Customer>> response=new ResponseStructure<List<Customer>>();
		response.setStatusCode(HttpStatus.FOUND.value());
		response.setMessage("Fetching all the customers from DB");
		response.setData(customers);
		return new ResponseEntity<ResponseStructure<List<Customer>>>(response,HttpStatus.FOUND);  
	}
	
	/*
	 * get customer by id
	 */
	public ResponseEntity<ResponseStructure<Optional<Customer>>> getCustomerById(Integer id){
		Optional<Customer> opt=customerDao.getCustomerById(id);
		ResponseStructure<Optional<Customer>> response=new ResponseStructure<Optional<Customer>>();
		if(!opt.isEmpty()) {
			response.setStatusCode(HttpStatus.FOUND.value());
			response.setMessage(" The Customer record are reterived successfully");
			response.setData(opt);
			return new ResponseEntity<ResponseStructure<Optional<Customer>>>(response,HttpStatus.FOUND);
		}
		else {
			throw new NoRecordFoundException("Customer is not exist in the DB with id: "+id);   
		}
	}
	
	
	/*
	 * update a customer
	 */
	public ResponseEntity<ResponseStructure<Customer>> updateCustomer(Customer customer){
		
		if(customer.getCustomreId()==null) {
			throw new IdNotFoundException("Id must be passed to update the customer");
		}
		Optional<Customer> opt=customerRepository.findById(customer.getCustomreId());
		ResponseStructure<Customer> response=new ResponseStructure<Customer>();
		
		if(opt.isPresent()) {
			response.setStatusCode(HttpStatus.FOUND.value());
			response.setMessage("Customer record with id "+customer.getCustomreId()+" is updated");
			response.setData(customerDao.updateCustomer(customer));
			return new ResponseEntity<ResponseStructure<Customer>>(response,HttpStatus.FOUND);
		}    
		else {
			throw new IdNotFoundException("Customer is not exist with id "+customer.getCustomreId()+"in the DB"); 
		}  
	}
	
	/*
	 * delete a customer
	 */
	public ResponseEntity<ResponseStructure<String>> deleteCustomer(Integer id){
		Optional<Customer> opt=customerRepository.findById(id);
		ResponseStructure<String> response=new ResponseStructure<String>();
		  
		if(opt.isPresent()) {
			customerRepository.delete(opt.get());
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("The record is deleted successfully by id ="+id);
			response.setData("i");
			return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.OK);
		}
		else
			throw new IdNotFoundException("The id is not exist in the DB");
	} 
	
	/*
	 * get by contact no.
	 */
	public ResponseEntity<ResponseStructure<Customer>> getCustomerByContact(String contact) {
	    Optional<Customer> optional = customerRepository.findByContactNo(contact);

	    ResponseStructure<Customer> response = new ResponseStructure<>();

	    if (optional.isPresent()) {
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Customer found with contact " + contact);
	        response.setData(optional.get());

	        return new ResponseEntity<ResponseStructure<Customer>>(response, HttpStatus.OK);
	    } else {
	        throw new NoRecordFoundException("Customer not found with contact " + contact);
	    }
	}
}
