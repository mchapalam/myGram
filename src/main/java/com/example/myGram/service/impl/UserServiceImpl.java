package com.example.myGram.service.impl;

import com.example.myGram.model.dto.UserResponse;
import com.example.myGram.repository.UserRepository;
import com.example.myGram.service.UserService;
import com.example.myGram.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> findAllUsers(){
        return userMapper.postListToResponseList(userRepository.findAll());
    }
}
