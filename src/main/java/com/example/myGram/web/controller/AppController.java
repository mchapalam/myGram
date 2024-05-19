package com.example.myGram.web.controller;

import com.example.myGram.model.dto.*;
import com.example.myGram.security.AppUserDetails;
import com.example.myGram.service.PostService;
import com.example.myGram.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/app")
@RequiredArgsConstructor
@Slf4j
public class AppController {

    private final Jackson2ObjectMapperBuilder mapperBuilder;
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
    public PostResponse createPost(@AuthenticationPrincipal AppUserDetails userDetails,
                                   @ModelAttribute UpsertPostRequest upsertPostRequest,
                                   @RequestParam("file")MultipartFile file) throws IOException {
        log.info("Calling create post");
        upsertPostRequest.setUserID(userDetails.getId());

        return postService.create(upsertPostRequest, file);
    }

    @CrossOrigin
    @GetMapping("/all_users_post")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('USER')")
    public List<PostResponse> findByAllUserPost(@AuthenticationPrincipal AppUserDetails userDetails){
        log.info("Calling all post");

        return postService.findAll();
    }

    @CrossOrigin
    @GetMapping("/post/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('USER')")
    public ResponseEntity<Object> findPostById(@AuthenticationPrincipal AppUserDetails userDetails,
                                               @PathVariable UUID id) throws IOException {
        log.info("Calling post by id");

        PostResponse postResponse = postService.findPostById(id);

        String base64ImageData = postService.getImageData(postResponse.getFile());

        postResponse.setBase64ImageData(base64ImageData);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/all_users")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('USER')")
    public List<UserResponse> findByAllUsers(@AuthenticationPrincipal AppUserDetails userDetails){
        log.info("Calling all users");

        return userService.findAllUsers();
    }


    @CrossOrigin
    @GetMapping("/user_all_posts/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('USER')")
    public List<PostResponse> findPostsByUser(@AuthenticationPrincipal AppUserDetails userDetails,
                                              @PathVariable UUID userId) throws IOException{
        log.info("Calling findPostsByUser {}", userId);

        return postService.findPostsByUserId(userId);
    }
}

