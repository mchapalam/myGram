package com.example.myGram.web.model;

import com.example.myGram.model.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CreateUserRequest {
    private String username;
    private String email;
    private String password;

}
