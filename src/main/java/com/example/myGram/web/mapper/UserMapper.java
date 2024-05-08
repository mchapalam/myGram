package com.example.myGram.web.mapper;

import com.example.myGram.model.dto.UserResponse;
import com.example.myGram.model.entity.User;
import com.example.myGram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

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
}
