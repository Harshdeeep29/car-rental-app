package in.hg.main.controller;

import org.springframework.http.MediaType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import static in.hg.main.helper.CarsSpecification.*;

import in.hg.main.exception.CarNotFoundException;
import in.hg.main.model.Cars;
import in.hg.main.repository.CarsRepository;
import org.springframework.core.io.Resource;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5000"})
public class CarController {

    @Autowired
    private CarsRepository carsRepository;

    @PostMapping("/car")
    Cars addCar(@RequestBody Cars addCar) {
        return carsRepository.save(addCar);
    }

    @GetMapping("/cars")
    List<Cars> viewCars(
            @RequestParam(required = false) String fuel,
            @RequestParam(required = false) Integer seats,
            @RequestParam(required = false) String search) {

        return carsRepository.findAll(
                hasFuel(fuel)
                        .and(hasSeats(seats))
                        .and(matchesSearch(search))
        );
    }

    @GetMapping("/car/{id}")
    Cars viewCarById(@PathVariable Long id) {
        return carsRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
    }

    @PutMapping("/car/{id}")
    Cars updateCars(@PathVariable Long id, @RequestBody Cars updatedcars) {
        return carsRepository.findById(id)
                .map(cars -> {
                    cars.setCarCategory(updatedcars.getCarCategory());
                    cars.setFuelType(updatedcars.getFuelType());
                    cars.setMileage(updatedcars.getMileage());
                    cars.setModel(updatedcars.getModel());
                    cars.setSeatCapacity(updatedcars.getSeatCapacity());
                    cars.setRentPerHour(updatedcars.getRentPerHour());

                    // NEW - allow admin to update rental status
                    if (updatedcars.getStatus() != null) {
                        cars.setStatus(updatedcars.getStatus());
                    }

                    return carsRepository.save(cars);
                }).orElseThrow(() -> new CarNotFoundException(id));
    }


    @DeleteMapping("/car/{id}")
    String deleteCar(@PathVariable Long id) {
        if (!carsRepository.existsById(id)) {
            throw new CarNotFoundException(id);
        }
        carsRepository.deleteById(id);
        return "Car with id " + id + " deleted successfully";
    }

    @PostMapping("/car/{id}/upload-image")
    Cars uploadCarImage(@RequestParam("image") MultipartFile file, @PathVariable Long id) throws IOException {
        Cars car = carsRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));

        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";
        File dir = new File(uploadDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        // Save file
        Files.write(filePath, file.getBytes());

        // Save the path or URL in DB
        car.setImagePath(fileName);
        return carsRepository.save(car);
    }

    @GetMapping("/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
        Path filePath = Paths.get(System.getProperty("user.dir"), "uploads").resolve(filename).normalize();
        UrlResource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new FileNotFoundException("File not found: " + filename);
        }

        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @PutMapping("/car/{id}/upload-image")
    Cars updateCarImage(@RequestParam("image") MultipartFile file,
                        @PathVariable Long id) throws IOException {
        Cars car = carsRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException(id));
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (car.getImagePath() != null) {
            Path oldImagePath = Paths.get(uploadDir, car.getImagePath());
            Files.deleteIfExists(oldImagePath);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        // Save the new file
        Files.write(filePath, file.getBytes());

        // Update the car's image path in DB
        car.setImagePath(fileName);

        return carsRepository.save(car);
    }
}
