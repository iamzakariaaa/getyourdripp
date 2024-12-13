package com.iamzakaria.dripp.service.auth;

import com.iamzakaria.dripp.dao.UserRepository;
import com.iamzakaria.dripp.entity.User;
import com.iamzakaria.dripp.enums.Role;
import com.iamzakaria.dripp.payloads.JwtResponse;
import com.iamzakaria.dripp.jwt.JwtUtils;
import com.iamzakaria.dripp.payloads.SignInRequest;
import com.iamzakaria.dripp.payloads.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public JwtResponse signup(SignUpRequest request) {
        try {
            User user = User.builder()
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.ROLE_CUSTOMER)
                    .build();
            userRepository.save(user);
            String jwt = jwtService.createToken(user.getEmail(), List.of(user.getRole()));
            return JwtResponse.builder().token(jwt).build();
        } catch (Exception e) {
            throw new RuntimeException("Error during signup", e);
        }
    }
    @Override
    public JwtResponse login(SignInRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = userRepository.findByEmail(request.getEmail());
            String jwt = jwtService.createToken(user.getEmail(), List.of(user.getRole()));
            return JwtResponse.builder().token(jwt).build();
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Invalid email or password", e);
        } catch (Exception e) {
            throw new RuntimeException("Error during login", e);
        }

    }
}
