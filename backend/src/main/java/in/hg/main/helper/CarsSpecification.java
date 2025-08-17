package in.hg.main.helper;

import org.springframework.data.jpa.domain.Specification;
import in.hg.main.model.Cars;

public class CarsSpecification {

    public static Specification<Cars> hasFuel(String fuel) {
        return (root, query, cb) -> {
            if (fuel == null || fuel.isEmpty() || fuel.equalsIgnoreCase("All")) {
                return cb.conjunction();
            }
            return cb.equal(cb.lower(root.get("fuelType")), fuel.toLowerCase());
        };
    }

    public static Specification<Cars> hasSeats(Integer seats) {
        return (root, query, cb) -> {
            if (seats == null || seats == 0) {
                return cb.conjunction();
            }
            return cb.equal(root.get("seatCapacity"), seats);
        };
    }

    public static Specification<Cars> matchesSearch(String search) {
        return (root, query, cb) -> {
            if (search == null || search.trim().isEmpty()) {
                return cb.conjunction();
            }
            String likePattern = "%" + search.toLowerCase() + "%";
            return cb.or(
                cb.like(cb.lower(root.get("model")), likePattern),
                cb.like(cb.lower(root.get("carCategory")), likePattern)
            );
        };
    }
}
