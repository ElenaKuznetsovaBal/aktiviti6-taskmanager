package com.aktiviti6.controller;

import com.aktiviti6.dto.LoginRequest;
import com.aktiviti6.dto.LoginResponse;
import com.aktiviti6.dto.RegisterRequest;
import com.aktiviti6.dto.ApiResponse;
import com.aktiviti6.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest registerRequest) {
        try {
            Map<String, String> response = authService.register(registerRequest);
            return ResponseEntity.ok(ApiResponse.success(response.get("message")));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Map<String, String> response = authService.login(loginRequest);
            LoginResponse loginResponse = new LoginResponse(response.get("token"), response.get("message"));
            return ResponseEntity.ok(loginResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
