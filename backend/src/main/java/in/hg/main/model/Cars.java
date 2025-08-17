package in.hg.main.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Cars {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String model;
	
	 @JsonProperty("car_category")
	private String carCategory;
	 
	    @JsonProperty("fuel_type")
	private String fuelType;
	    
	    @JsonProperty("seat_capacity")
	private int seatCapacity;
	private double mileage;
	private String imagePath;
	  @JsonProperty("rent_per_hour") 
	    private double rentPerHour; 
	  
	  private String status = "available";
	  public String getStatus() {
	        return status;
	    }
	    public void setStatus(String status) {
	        this.status = status;
	    }


		

	public Long getId() {
	    return id;
	}

	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getCarCategory() {
		return carCategory;
	}
	public void setCarCategory(String carCategory) {
		this.carCategory = carCategory;
	}
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	public int getSeatCapacity() {
		return seatCapacity;
	}
	public void setSeatCapacity(int seatCapacity) {
		this.seatCapacity = seatCapacity;
	}
	public double getMileage() {
		return mileage;
	}
	public void setMileage(double mileage) {
		this.mileage = mileage;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	 public double getRentPerHour() { return rentPerHour; }
	    public void setRentPerHour(double rentPerHour) { this.rentPerHour = rentPerHour; }

}
