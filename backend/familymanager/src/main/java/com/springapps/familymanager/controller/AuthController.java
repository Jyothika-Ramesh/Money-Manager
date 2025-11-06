package com.springapps.familymanager.controller;

import com.springapps.familymanager.dto.AuthRequest;
import com.springapps.familymanager.dto.AuthResponse;
import com.springapps.familymanager.entity.User;
import com.springapps.familymanager.service.UserService;
import com.springapps.familymanager.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private UserService service;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        user.setPassword(encoder.encode(user.getPassword()));
        String msg = service.addUser(user);
        if(Objects.equals(msg, "user registered")) {
            return ResponseEntity.ok("User registered");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exist");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        User user = service.getByEmail(request.getEmail());

        if (user != null && encoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user);
            return ResponseEntity.ok(new AuthResponse(token, user.getRole()));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}


