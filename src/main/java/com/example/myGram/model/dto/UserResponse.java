package com.example.myGram.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private UUID id;
    private String userName;
    private String email;
    private String lastName;
    private String firstName;
    private Short age;
    private String avatar;
    private String city;
    private String country;
    private String status;

    private Instant createAt;
    private Instant onlineAt;

    private List<PostResponse> post;
}
