package com.example.myGram.service.impl;

import com.example.myGram.model.dto.PostResponse;
import com.example.myGram.model.dto.UpsertPostRequest;
import com.example.myGram.model.entity.Post;
import com.example.myGram.repository.PostRepository;
import com.example.myGram.repository.UserRepository;
import com.example.myGram.service.PostService;
import com.example.myGram.web.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public List<PostResponse> findAll() {
        return postMapper.postListToResponseList(postRepository.findAll());
    }

    @Override
    public PostResponse create(UpsertPostRequest post) {
        return postMapper.postToResponse(postRepository.save(
                postMapper.requestToPost(post)));
    }

    @Override
    public List<PostResponse> findPostsByUser(String username) {
        UUID userId = userRepository.findByUsername(username).get().getId();

        return postMapper.postListToResponseList(postRepository.getPostByUsername(userId));
    }
}
