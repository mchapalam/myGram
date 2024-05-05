package com.example.myGram.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/app")
public class AppController {
    @CrossOrigin
    @GetMapping("/all")
    public String allAccess(){
        return "Public response data";
    }

    @CrossOrigin
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminAccess(){
        return "Admin response data";
    }

    @CrossOrigin
    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('USER')")
    public String userAccess(){
        return "User response data";
    }


}
