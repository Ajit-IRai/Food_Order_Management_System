package foodOrderSystem.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import foodOrderSystem.entity.Resturant;
import foodOrderSystem.repository.ResturantRepository;

@Repository
public class ResturantDao {

	@Autowired
	ResturantRepository resturantRepository;
	
	/*
	 * save resturant
	 */
	public Resturant saveResturant(Resturant resturant) {
		return	resturantRepository.save(resturant);    
	}
	
	
	/*
	 * get all resturant
	 */
	public List<Resturant> getAllResturants(){
		return resturantRepository.findAll();  
	}
	
	/*
	 * get by id
	 */
	public Optional<Resturant> getResturantById(Integer id){
		return	resturantRepository.findById(id);    
	}
	
	/*
	 * update the resturant
	 */
	public Resturant updateResturant(Resturant resturant) {
		return resturantRepository.save(resturant);  
	}
	
	/*
	 * delete a resturant by id
	 */
	public String deleteResturant(Integer id) {
		return "The Resturant is deleted by this id: "+id;
	}
	
	/*
	 * get resturant by location
	 */
	public Optional<Resturant> getResturantByLocation(String location){
		return resturantRepository.findBylocation(location);  
	}
	
	/*
	 *get resturant by name 
	 */
	public Optional<Resturant> getResturantByName(String resturantName){
		return resturantRepository.findByresturantName(resturantName);  
	}
	
    /*
     * pagination and sorting
     */
	 public Page<Resturant> getAllResturants(int page, int size, String sortBy, String direction) {

	        Sort sort = direction.equalsIgnoreCase("asc")
	                ? Sort.by(sortBy).ascending()
	                : Sort.by(sortBy).descending();

	        Pageable pageable = PageRequest.of(page, size, sort);

	        return resturantRepository.findAll(pageable);
	    }
	
	
	 public Optional<Resturant> getResturantById(int id) {
	        return resturantRepository.findById(id);
	    }
}
