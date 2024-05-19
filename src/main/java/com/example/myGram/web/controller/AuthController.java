package com.example.myGram.web.controller;

import com.example.myGram.exception.AlreadyExitsException;
import com.example.myGram.model.dto.UserForPostResponse;
import com.example.myGram.model.dto.UserResponse;
import com.example.myGram.repository.UserRepository;
import com.example.myGram.security.AppUserDetails;
import com.example.myGram.security.SecurityService;
import com.example.myGram.service.UserService;
import com.example.myGram.web.mapper.UserMapper;
import com.example.myGram.web.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserRepository userRepository;
    private final UserService  userService;
    private final UserMapper userMapper;
    private final SecurityService securityService;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authUser(@RequestBody LoginRequest loginRequest){
        log.info("Call sigin api");
        return ResponseEntity.ok(securityService.authUser(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> authUser(@RequestBody CreateUserRequest createUserRequest){
        log.info("Call register api");
        if (userRepository.existsByUsername(createUserRequest.getUsername()))
            throw new AlreadyExitsException("Username invalid");

        if (userRepository.existsByUsername(createUserRequest.getEmail()))
            throw new AlreadyExitsException("Email invalid");

        securityService.register(createUserRequest);

        LoginRequest loginRequest = new LoginRequest(createUserRequest.getUsername(), createUserRequest.getPassword());

        return ResponseEntity.ok(securityService.authUser(loginRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request){
        log.info("Call refresh-token api");
        return ResponseEntity.ok(securityService.tokenRefresh(request));
    }

    @GetMapping("/checkAuth")
    public ResponseEntity<UserForPostResponse> refreshToken(@AuthenticationPrincipal AppUserDetails appUserDetails){
        return ResponseEntity.ok(userService.findUserByUsername(appUserDetails.getUsername()));
    }

    @PostMapping("/logout")
    public ResponseEntity<SimpleResponse> logoutUser(@AuthenticationPrincipal UserDetails userDetails){
        log.info("Call logout api");
        securityService.logout();

        return ResponseEntity.ok(new SimpleResponse("User logout. Username is: " + userDetails.getUsername()));
    }
}
