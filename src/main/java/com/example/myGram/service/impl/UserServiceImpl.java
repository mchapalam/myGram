package com.example.myGram.service.impl;

import com.example.myGram.model.dto.UserForPostResponse;
import com.example.myGram.model.dto.UserResponse;
import com.example.myGram.repository.PostRepository;
import com.example.myGram.repository.UserRepository;
import com.example.myGram.service.UserService;
import com.example.myGram.web.mapper.PostMapper;
import com.example.myGram.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PostMapper postMapper;

    public List<UserResponse> findAllUsers(){
        return userMapper.postListToResponseList(userRepository.findAll());
    }

    @Override
    public UserForPostResponse findUserByUsername(String username) {
        UserForPostResponse userResponse = userMapper.userToUserForPostResponse(userRepository.findByUsername(username).get());

        userResponse.setPosts(postMapper.postListToResponseList(postRepository
                        .getPostByUsername(userResponse.getId())));

        return userResponse;
    }
}
