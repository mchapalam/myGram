package com.example.myGram.service;

import com.example.myGram.model.dto.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> findAllUsers();

    UserResponse findUserByUsername(String username);
}
