package com.example.myGram.web.mapper;

import com.example.myGram.model.dto.PostResponse;
import com.example.myGram.model.dto.UserForPostResponse;
import com.example.myGram.model.dto.UserResponse;
import com.example.myGram.model.entity.Post;
import com.example.myGram.model.entity.User;
import com.example.myGram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UserRepository userRepository;

    public UserResponse userToResponse(String username){
        UserResponse userResponse = new UserResponse();

        User userTemp = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userResponse.setId(userTemp.getId());
        userResponse.setEmail(userTemp.getEmail());
        userResponse.setUserName(username);
        userResponse.setLastName(userTemp.getLastName());
        userResponse.setFirstName(userTemp.getFirstName());
        userResponse.setAge(userTemp.getAge());
        userResponse.setCity(userTemp.getCity());
        userResponse.setCountry(userTemp.getCountry());
        userResponse.setStatus(userTemp.getStatus());
        userResponse.setCreateAt(userTemp.getCreateAt());
        userResponse.setAvatar(userTemp.getAvatar());

        return userResponse;
    }

    public UserForPostResponse userToUserForPostResponse(User user){
        UserForPostResponse userResponse = new UserForPostResponse();

        userResponse.setUsername(user.getUsername());
        userResponse.setAvatar(user.getAvatar());

        return userResponse;
    }

    public UserResponse userToResponse(User user){
        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setUserName(user.getUsername());
        userResponse.setLastName(user.getLastName());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setAge(user.getAge());
        userResponse.setCity(user.getCity());
        userResponse.setCountry(user.getCountry());
        userResponse.setStatus(user.getStatus());
        userResponse.setCreateAt(user.getCreateAt());
        userResponse.setAvatar(user.getAvatar());

        return userResponse;
    }

    public List<UserResponse> postListToResponseList(List<User> users){
        return users.stream()
                .map(this::userToResponse)
                .collect(Collectors.toList());
    }
}
