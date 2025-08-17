package in.hg.main.controller;

import in.hg.main.model.Bookings;
import in.hg.main.model.Payments;
import in.hg.main.model.Cars;
import in.hg.main.repository.BookingRepository;
import in.hg.main.repository.PaymentsRepository;
import in.hg.main.repository.CarsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "http://localhost:5000")
public class PaymentsController {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CarsRepository carsRepository;

    @PostMapping("/pay")
    public Payments processPayment(
            @RequestParam Long bookingId,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String method
    ) {
        // 1. Get booking
        Bookings booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // 2. Create payment record
        Payments payment = new Payments();
        payment.setBooking(booking);
        payment.setAmount(amount);
        payment.setPaymentMethod(method != null ? method : "Dummy Gateway");
        payment.setStatus("SUCCESS"); // simulate success
        paymentsRepository.save(payment);

        // 3. Update booking status to confirmed
        booking.setStatus("confirmed");
        bookingRepository.save(booking);

        // 4. Update car status to rented
        Cars car = booking.getCar();
        car.setStatus("rented");
        carsRepository.save(car);

        return payment;
    }
}
