package com.example.myGram.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private UUID id;
    private String userName;
    private String password;
    private String email;
    private String lastName;
    private String firstName;
    private Short age;
    private String avatar;
    private String city;
    private String country;
    private String status;

    private String token;

    private Instant createAt;
    private Instant onlineAt;
}
