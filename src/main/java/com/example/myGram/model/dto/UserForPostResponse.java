package com.example.myGram.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForPostResponse {
    private String username;
    private String avatar;

    private List<PostResponse> posts;
}
