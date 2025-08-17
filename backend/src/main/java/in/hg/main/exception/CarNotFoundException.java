package in.hg.main.exception;

public class CarNotFoundException extends RuntimeException {

	public CarNotFoundException(Long id) {
		super("Car not Founddddd with ID : "+id);
	}
}
