package foodOrderSystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import foodOrderSystem.dto.ResponseStructure;
import foodOrderSystem.entity.MenuItem;
import foodOrderSystem.entity.Resturant;
import foodOrderSystem.service.ResturantService;

@RestController
@RequestMapping("/resturant")
public class ResturantController {

	@Autowired
	ResturantService resturantService; 
	
	@PutMapping
	public ResponseEntity<ResponseStructure<Resturant>> saveResturant(@RequestBody Resturant resturant){
		return resturantService.saveResturant(resturant); 
	}
	
	@GetMapping("/resturants")
	public ResponseEntity<ResponseStructure<List<Resturant>>> getAllResturants(){
		return resturantService.getAllResturants();  
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Optional<Resturant>>> getResturantById(@PathVariable Integer id){
		return resturantService.getResturantById(id);
	}
	
	@PostMapping("/api")
	public ResponseEntity<ResponseStructure<Resturant>> updateResturant(@RequestBody Resturant resturant){
		return resturantService.updateResturant(resturant);  
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteResturant(@PathVariable Integer id){
		return resturantService.deleteResturant(id);        
	}
	
	@GetMapping("/api/{location}")
	public ResponseEntity<ResponseStructure<Resturant>> getResturantByLocation(@PathVariable String location){
		return resturantService.getResturantByLocation(location);   
	} 
	
	@GetMapping("/rest/{resturantName}")
	public ResponseEntity<ResponseStructure<Resturant>> getResturantByName(@PathVariable String resturantName){
		return resturantService.getResturantByName(resturantName);   
	}
	
	 @GetMapping("/all")
	    public ResponseEntity<ResponseStructure<Page<Resturant>>> getAllResturants(
	            @RequestParam(defaultValue = "0") int page,
	            @RequestParam(defaultValue = "5") int size,
	            @RequestParam(defaultValue = "id") String sortBy,
	            @RequestParam(defaultValue = "asc") String direction) {
 
	        return resturantService.getAllResturants(page, size, sortBy, direction);
	    }
	 
	 //   Get menu of restaurant
	    @GetMapping("/{id}/menu")
	    public ResponseEntity<ResponseStructure<List<MenuItem>>> getMenuOfResturant(
	            @PathVariable int id) {

	        return resturantService.getMenuOfResturant(id);
	    }
}
