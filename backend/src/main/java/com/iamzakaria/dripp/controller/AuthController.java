package com.iamzakaria.dripp.controller;

import com.iamzakaria.dripp.service.auth.AuthService;
import com.iamzakaria.dripp.payloads.JwtResponse;
import com.iamzakaria.dripp.payloads.SignInRequest;
import com.iamzakaria.dripp.payloads.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<JwtResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}