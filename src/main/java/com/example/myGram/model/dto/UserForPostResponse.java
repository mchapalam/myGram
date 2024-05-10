package com.example.myGram.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForPostResponse {

    private UUID id;
    private String username;
    private String avatar;

    private List<PostResponse> posts;
}
