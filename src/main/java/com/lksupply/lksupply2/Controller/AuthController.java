package com.lksupply.lksupply2.Controller;

import com.lksupply.lksupply2.entity.User;
import com.lksupply.lksupply2.repository.UserRepository;
import com.lksupply.lksupply2.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JwtUtils jwtUtils;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent())
            return ResponseEntity.badRequest().body("Username taken");
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> data) {
        User user = userRepo.findByUsername(data.get("username")).orElse(null);
        if (user != null && encoder.matches(data.get("password"), user.getPassword())) {
            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user.getUsername(), user.getRole());
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(Map.of("message", "Login Success", "role", user.getRole(), "name", user.getUsername()));
        }
        return ResponseEntity.status(401).body("Invalid Credentials");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtUtils.getCleanJwtCookie().toString()).body("Logged out");
    }
}
