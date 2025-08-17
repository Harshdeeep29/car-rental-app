package in.hg.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.hg.main.model.Bookings;

public interface BookingRepository extends JpaRepository<Bookings, Long>{

}
