package com.example.myGram.web.mapper;

import com.example.myGram.model.dto.PostResponse;
import com.example.myGram.model.dto.UpsertPostRequest;
import com.example.myGram.model.entity.Post;
import com.example.myGram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class PostMapper {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public Post requestToPost(UpsertPostRequest upsertPostRequest){
        Post post = new Post();

        post.setTitle(upsertPostRequest.getTitle());
        post.setDateCreate(Instant.now());
        post.setUser(userRepository.findById(upsertPostRequest.getUserID()).get());

        return post;
    }

    public PostResponse postToResponse(Post post){
        PostResponse postResponse = new PostResponse();

        postResponse.setId(post.getId());
        postResponse.setTitle(post.getTitle());
        postResponse.setFile(post.getFile());
        postResponse.setDateCreate(post.getDateCreate());

        return postResponse;
    }

    public List<PostResponse> postListToResponseList(List<Post> posts){
        return posts.stream()
                .map(this::postToResponse)
                .collect(Collectors.toList());
    }
}
