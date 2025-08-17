package in.hg.main.controller;

import in.hg.main.model.User;
import in.hg.main.repository.UserRepository;
import in.hg.main.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5000") // adjust to your frontend port
public class UserContoller {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        user.setPasswordHash(hashPassword(user.getPasswordHash()));
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User loginRequest) {
        return userRepository.findByEmail(loginRequest.getEmail())
            .<Map<String, Object>>map(existingUser -> {
                String hashedPassword = hashPassword(loginRequest.getPasswordHash());
                if (!hashedPassword.equals(existingUser.getPasswordHash())) {
                    return Map.of("status", "error", "message", "Invalid password");
                }

                // ✅ Generate JWT
                String token = JwtUtil.generateToken(existingUser.getEmail());

                return Map.of(
                    "status", "success",
                    "message", "Login successful",
                    "token", token, // send JWT to frontend
                    "email", existingUser.getEmail(),
                    "id", existingUser.getId()
                );
            })
            .orElse(Map.of("status", "error", "message", "User not found"));
    }
}
