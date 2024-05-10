package com.example.myGram.web.controller;

import com.example.myGram.model.dto.*;
import com.example.myGram.security.AppUserDetails;
import com.example.myGram.service.PostService;
import com.example.myGram.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/app")
@RequiredArgsConstructor
@Slf4j
public class AppController {
    private final PostService postService;
    private final UserService userService;

    @CrossOrigin
    @PostMapping("/user")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('USER')")
    public UserForPostResponse userAccess(@AuthenticationPrincipal AppUserDetails userDetails, @RequestBody UpsertUserRequest userRequest){
        log.info("Call user {}", userRequest.getUsername());
        return userService.findUserByUsername(userRequest.getUsername());
    }

    @CrossOrigin
    @PostMapping("/create_post")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('USER')")
    public PostResponse createPost(@AuthenticationPrincipal AppUserDetails userDetails, @RequestBody UpsertPostRequest upsertPostRequest){
        log.info("Calling create post");

        upsertPostRequest.setUserId(userDetails.getId());

        return postService.create(upsertPostRequest);
    }

    @CrossOrigin
    @GetMapping("/all_users_post")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('USER')")
    public List<PostResponse> findByAllUserPost(@AuthenticationPrincipal AppUserDetails userDetails){
        log.info("Calling all post");

        return postService.findAll();
    }

    @CrossOrigin
    @GetMapping("/all_users")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('USER')")
    public List<UserResponse> findByAllUsers(@AuthenticationPrincipal AppUserDetails userDetails){
        log.info("Calling all users");

        return userService.findAllUsers();
    }


    @CrossOrigin
    @PostMapping("/user_all_posts")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('USER')")
    public List<PostResponse> findPostsByUser(@AuthenticationPrincipal AppUserDetails userDetails, @RequestBody UpsertUserRequest userRequest){
        log.info("Calling findPostsByUser {}", userRequest.getUsername());

        return postService.findPostsByUser(userRequest.getUsername());
    }
}

