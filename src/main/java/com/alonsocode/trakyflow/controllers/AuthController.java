package com.alonsocode.trakyflow.controllers;

import com.alonsocode.trakyflow.models.User;
import com.alonsocode.trakyflow.services.AuthService;
import com.alonsocode.trakyflow.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registeredUser = authService.register(user);
            String token = jwtUtil.generateToken(registeredUser);
            
            Map<String, Object> response = new HashMap<>();
            response.put("user", registeredUser);
            response.put("token", token);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error en el registro: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            
            // Implementación temporal - necesitarás cargar el usuario para generar el token
            Map<String, String> response = new HashMap<>();
            response.put("token", "token-temporal-para-pruebas");
            response.put("message", "Login exitoso - implementación en progreso");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error en login: " + e.getMessage());
        }
    }
    
    // Clase interna para el request de login
    public static class LoginRequest {
        private String email;
        private String password;
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
