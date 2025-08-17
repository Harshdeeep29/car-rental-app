package in.hg.main.controller;

import in.hg.main.dto.BookingRequest;
import in.hg.main.model.Bookings;
import in.hg.main.model.Cars;
import in.hg.main.model.User;
import in.hg.main.repository.BookingRepository;
import in.hg.main.repository.CarsRepository;
import in.hg.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CarsRepository carsRepository;

    @Autowired
    private UserRepository userRepository;

    // Create booking from DTO
    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<Bookings> createBooking(
            @RequestParam("carId") Long carId,
            @RequestParam("userId") Long userId,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime,
            @RequestParam("totalCharge") BigDecimal totalCharge,
            @RequestParam(value = "aadharCard", required = false) MultipartFile aadharCard,
            @RequestParam(value = "drivingLicense", required = false) MultipartFile drivingLicense
    ) {
        // Find related entities
        Cars car = carsRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        // Prevent booking if already rented
        if ("rented".equalsIgnoreCase(car.getStatus())) {
            throw new RuntimeException("Car is already rented");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Save files
        String uploadDir = "C:\\Users\\gorad\\Documents\\workspace-spring-tools-for-eclipse-4.31.0.RELEASE\\backend\\uploadedDocs";
        String aadharPath = null;
        String licensePath = null;

        try {
            if (aadharCard != null && !aadharCard.isEmpty()) {
                aadharPath = uploadDir + "\\" + aadharCard.getOriginalFilename();
                aadharCard.transferTo(new java.io.File(aadharPath));
            }
            if (drivingLicense != null && !drivingLicense.isEmpty()) {
                licensePath = uploadDir + "\\" + drivingLicense.getOriginalFilename();
                drivingLicense.transferTo(new java.io.File(licensePath));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving files: " + e.getMessage());
        }

        // Create booking
        Bookings booking = new Bookings();
        booking.setCar(car);
        booking.setUser(user);
        booking.setStartTime(LocalDateTime.parse(startTime));
        booking.setEndTime(LocalDateTime.parse(endTime));
        booking.setTotalCharge(totalCharge);
        booking.setAadharCardPath(aadharPath);
        booking.setDrivingLicensePath(licensePath);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setStatus("pending");

        return ResponseEntity.ok(bookingRepository.save(booking));
    }

    @PutMapping("/{id}/status")
    public Bookings updateStatus(@PathVariable Long id, @RequestParam String status) {
        Bookings booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        booking.setStatus(status);
        bookingRepository.save(booking);

        Cars car = booking.getCar();
        if ("confirmed".equalsIgnoreCase(status)) {
            car.setStatus("rented");
        } else if ("cancelled".equalsIgnoreCase(status) || "completed".equalsIgnoreCase(status)) {
            car.setStatus("available");
        }
        carsRepository.save(car);

        return booking;
    }




    @GetMapping("/all")
    public List<Bookings> getAllBookings() {
        return bookingRepository.findAll();
    }

    @GetMapping("/{id}")
    public Bookings getBookingById(@PathVariable Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
    }



    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingRepository.deleteById(id);
        return "Booking deleted successfully";
    }
}
