package in.hg.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import in.hg.main.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
