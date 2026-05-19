package foodOrderSystem.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Resturant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer resturantId;
	
	private String resturantName;
	private String location;
	
	@OneToMany(mappedBy = "resturant")
	@JsonIgnore
	private List<MenuItem> menuItems;

	
	public Integer getResturantId() {
		return resturantId;
	}

	public void setResturantId(Integer resturantId) {
		this.resturantId = resturantId;
	}

	public String getResturantName() {
		return resturantName;
	}

	public void setResturantName(String resturantName) {
		this.resturantName = resturantName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	
	
}
