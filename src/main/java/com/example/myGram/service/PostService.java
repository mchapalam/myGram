package com.example.myGram.service;

import com.example.myGram.model.dto.PostResponse;
import com.example.myGram.model.dto.UpsertPostRequest;
import com.example.myGram.model.entity.Post;

import java.util.List;

public interface PostService{
    List<PostResponse> findAll();

    PostResponse create(UpsertPostRequest post);
}
