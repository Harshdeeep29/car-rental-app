package in.hg.main.repository;
 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import in.hg.main.model.Cars;

public interface CarsRepository extends JpaRepository<Cars, Long >, JpaSpecificationExecutor<Cars> {
	
}
