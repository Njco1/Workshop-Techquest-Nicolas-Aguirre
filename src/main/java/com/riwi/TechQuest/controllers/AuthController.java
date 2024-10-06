package com.riwi.TechQuest.controllers;

import com.riwi.TechQuest.application.dtos.requests.LoginRequest;
import com.riwi.TechQuest.application.services.impl.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth") // Mapeo actualizado
public class AuthController {

    @Autowired
    private AuthService authService; // Inyecta el AuthService

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            String jwtToken = authService.login(loginRequest); // Usa el AuthService para iniciar sesión
            return ResponseEntity.ok(jwtToken); // Retorna el token
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body("Error en el servidor");
        }
    }
}

