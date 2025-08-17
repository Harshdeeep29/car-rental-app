package in.hg.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import in.hg.main.model.Payments;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {
}
