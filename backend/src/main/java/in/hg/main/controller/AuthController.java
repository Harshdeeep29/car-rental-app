package in.hg.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.hg.main.model.AdminUser;
import in.hg.main.repository.AdminUserRepository;
import in.hg.main.security.JwtUtil;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AdminUserRepository adminUserRepository;

    // Register new admin
    @PostMapping("/register")
    public ResponseEntity<?> registerAdminUser(@RequestBody AdminUser adminUser) {
        // Check if email already exists
        if (adminUserRepository.findByEmail(adminUser.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        return ResponseEntity.ok(adminUserRepository.save(adminUser));
    }

    // Login admin
    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody AdminUser loginRequest) {
        Optional<AdminUser> user = adminUserRepository.findByEmail(loginRequest.getEmail());
        if (user.isPresent() && user.get().getPassword().equals(loginRequest.getPassword())) {
            String token = JwtUtil.generateToken(user.get().getEmail());
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }

}
