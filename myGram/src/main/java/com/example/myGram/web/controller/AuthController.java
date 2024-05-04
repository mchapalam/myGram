package com.example.myGram.web.controller;

import com.example.myGram.exception.AlreadyExitsException;
import com.example.myGram.repository.UserRepository;
import com.example.myGram.security.SecurityService;
import com.example.myGram.web.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserRepository userRepository;

    private final SecurityService securityService;

    @PostMapping("/sigin")
    public ResponseEntity<AuthResponse> authUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(securityService.authUser(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<SimpleResponse> authUser(@RequestBody CreateUserRequest createUserRequest){
        if (userRepository.existsByUsername(createUserRequest.getUsername()))
            throw new AlreadyExitsException("Username valid");

        if (userRepository.existsByUsername(createUserRequest.getEmail()))
            throw new AlreadyExitsException("Email valid");

        securityService.register(createUserRequest);

        return ResponseEntity.ok(new SimpleResponse("User created"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok(securityService.tokenRefresh(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<SimpleResponse> logoutUser(@AuthenticationPrincipal UserDetails userDetails){
        securityService.logout();

        return ResponseEntity.ok(new SimpleResponse("User logout. Username is: " + userDetails.getUsername()));
    }
}
