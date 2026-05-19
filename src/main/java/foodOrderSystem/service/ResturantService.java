package foodOrderSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import foodOrderSystem.dao.ResturantDao;
import foodOrderSystem.dto.ResponseStructure;
import foodOrderSystem.entity.Customer;
import foodOrderSystem.entity.MenuItem;
import foodOrderSystem.entity.Resturant;
import foodOrderSystem.exception.IdNotFoundException;
import foodOrderSystem.exception.NoRecordFoundException;
import foodOrderSystem.repository.ResturantRepository;

@Service
public class ResturantService {

	@Autowired
	ResturantRepository resturantRepository;
	
	@Autowired
	ResturantDao resturantDao;   
	
	/*
	 * Resturant is save successfully
	 */
	public ResponseEntity<ResponseStructure<Resturant>> saveResturant(Resturant resturant){
		
		ResponseStructure<Resturant> response=new ResponseStructure<Resturant>();
		
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Resturant is saved successfully in DB"); 
		response.setData(resturantDao.saveResturant(resturant));
		
		return  new ResponseEntity<ResponseStructure<Resturant>>(response,HttpStatus.OK); 
		
	}   

	/*
	 * get all resturant
	 */  
	public ResponseEntity<ResponseStructure<List<Resturant>>> getAllResturants(){
		List<Resturant> resturants=resturantDao.getAllResturants();
		ResponseStructure<List<Resturant>> response=new ResponseStructure<List<Resturant>>();
		response.setStatusCode(HttpStatus.FOUND.value());
		response.setMessage("Fetching all the Resturants from DB");
		response.setData(resturants);
		return new ResponseEntity<ResponseStructure<List<Resturant>>>(response,HttpStatus.FOUND);  
	}
	
	/*
	 * get resturant by id
	 */
	public ResponseEntity<ResponseStructure<Optional<Resturant>>> getResturantById(Integer id){
		Optional<Resturant> opt=resturantDao.getResturantById(id);
		ResponseStructure<Optional<Resturant>> response=new ResponseStructure<Optional<Resturant>>();
		if(!opt.isEmpty()) {
			response.setStatusCode(HttpStatus.FOUND.value());
			response.setMessage(" The Resturant record are reterived successfully");
			response.setData(opt);
			return new ResponseEntity<ResponseStructure<Optional<Resturant>>>(response,HttpStatus.FOUND);
		}
		else {
			throw new NoRecordFoundException("Resturant is not exist in the DB with id: "+ id);   
		}
	}
	
	/*
	 * update the resturant
	 */
	public ResponseEntity<ResponseStructure<Resturant>> updateResturant(Resturant resturant){
		
		if(resturant.getResturantId()==null) {
			throw new IdNotFoundException("Id must be passed to update the Resturant");
		}
		Optional<Resturant> opt=resturantRepository.findById(resturant.getResturantId());
		ResponseStructure<Resturant> response=new ResponseStructure<Resturant>();
		
		if(opt.isPresent()) {
			response.setStatusCode(HttpStatus.FOUND.value());
			response.setMessage("Resturant record with id "+resturant.getResturantId()+" is updated");
			response.setData(resturantDao.updateResturant(resturant));
			return new ResponseEntity<ResponseStructure<Resturant>>(response,HttpStatus.FOUND);
		}    
		else {
			throw new IdNotFoundException("Resturant is not exist with id "+resturant.getResturantId()+ " in the DB"); 
		}  
	}
	
	/*
	 * deleted resturant by id
	 */
	public ResponseEntity<ResponseStructure<String>> deleteResturant(Integer id){
		Optional<Resturant> opt=resturantRepository.findById(id);
		ResponseStructure<String> response=new ResponseStructure<String>();
		  
		if(opt.isPresent()) {
			resturantRepository.delete(opt.get());
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("The record is deleted successfully by id ="+id);
			response.setData("i");
			return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.OK);
		}
		else
			throw new IdNotFoundException("The id is not exist in the DB");
	} 
	
	/*
	 * get reaturant by location
	 */
	public ResponseEntity<ResponseStructure<Resturant>> getResturantByLocation(String location) {
	    Optional<Resturant> optional = resturantRepository.findBylocation(location);

	    ResponseStructure<Resturant> response = new ResponseStructure<>();

	    if (optional.isPresent()) {
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Resturant found with loaction " + location);
	        response.setData(optional.get());

	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        throw new NoRecordFoundException("Resturant not found with location " + location);
	    }
	}
	
	/*
	 * get resturant by name
	 */
	public ResponseEntity<ResponseStructure<Resturant>> getResturantByName(String resturantName) {
	    Optional<Resturant> optional = resturantRepository.findByresturantName(resturantName);
  
	    ResponseStructure<Resturant> response = new ResponseStructure<>();

	    if (optional.isPresent()) {
	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Resturant found with resturantName " + resturantName);
	        response.setData(optional.get());

	        return new ResponseEntity<ResponseStructure<Resturant>>(response, HttpStatus.OK);
	    } else {
	        throw new NoRecordFoundException("Resturant not found with resturantName " +resturantName);
	    }
	}
	
	  public ResponseEntity<ResponseStructure<Page<Resturant>>> getAllResturants(
	            int page, int size, String sortBy, String direction) {

	        Page<Resturant> resturantPage =
	                resturantDao.getAllResturants(page, size, sortBy, direction);

	        ResponseStructure<Page<Resturant>> response = new ResponseStructure<>();

	        response.setStatusCode(HttpStatus.OK.value());
	        response.setMessage("Resturants fetched successfully");
	        response.setData(resturantPage);

	        return new ResponseEntity<ResponseStructure<Page<Resturant>>>(response, HttpStatus.OK);
	 }
	  
	  // Get Menu Items 
	    public ResponseEntity<ResponseStructure<List<MenuItem>>> getMenuOfResturant(int id) {

	        Optional<Resturant> opt = resturantDao.getResturantById(id);

	        if (opt.isEmpty()) {
	            throw new IdNotFoundException("Resturant not found with id " + id);
	        }

	        Resturant resturant = opt.get();

	        List<MenuItem> menu = resturant.getMenuItems(); 

	        if (menu == null || menu.isEmpty()) {
	            throw new IdNotFoundException("No menu items found for this restaurant");
	        }

	        ResponseStructure<List<MenuItem>> structure = new ResponseStructure<>();
	        structure.setStatusCode(HttpStatus.OK.value());
	        structure.setMessage("Menu fetched successfully");
	        structure.setData(menu);

	        return new ResponseEntity<ResponseStructure<List<MenuItem>>>(structure, HttpStatus.OK);
	    }
	  
	  
}
