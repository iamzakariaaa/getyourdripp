package com.iamzakaria.dripp.service.auth;

import com.iamzakaria.dripp.payloads.JwtResponse;
import com.iamzakaria.dripp.payloads.SignInRequest;
import com.iamzakaria.dripp.payloads.SignUpRequest;

public interface AuthService {
    JwtResponse signup(SignUpRequest request);

    JwtResponse login(SignInRequest request);
}