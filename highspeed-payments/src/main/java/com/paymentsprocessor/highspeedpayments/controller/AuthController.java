package com.paymentsprocessor.highspeedpayments.controller;

import com.paymentsprocessor.highspeedpayments.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> payload) {
        authService.registerUser(
            payload.get("username"),
            payload.get("email"),
            payload.get("password")
        );
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}