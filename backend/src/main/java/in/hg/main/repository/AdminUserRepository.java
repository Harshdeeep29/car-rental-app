package in.hg.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import in.hg.main.model.AdminUser;

public interface AdminUserRepository extends JpaRepository<AdminUser, Integer>{

    Optional<AdminUser> findByEmail(String email);

}
