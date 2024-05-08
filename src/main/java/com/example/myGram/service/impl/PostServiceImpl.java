package com.example.myGram.service.impl;

import com.example.myGram.model.dto.PostResponse;
import com.example.myGram.model.dto.UpsertPostRequest;
import com.example.myGram.model.entity.Post;
import com.example.myGram.repository.PostRepository;
import com.example.myGram.service.PostService;
import com.example.myGram.web.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;

    private final PostRepository postRepository;

    @Override
    public List<PostResponse> findAll() {
        return postMapper.postListToResponseList(postRepository.findAll());
    }

    @Override
    public PostResponse create(UpsertPostRequest post) {
        return postMapper.postToResponse(postRepository.save(
                postMapper.requestToPost(post)));
    }
}
