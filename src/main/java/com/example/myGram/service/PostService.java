package com.example.myGram.service;

import com.example.myGram.model.dto.PostResponse;
import com.example.myGram.model.dto.UpsertPostRequest;
import com.example.myGram.model.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface PostService{
    List<PostResponse> findAll();

    PostResponse create(UpsertPostRequest post, MultipartFile file) throws IOException;

    List<PostResponse> findPostsByUser(String username);

    PostResponse findPostById(UUID id);

    String getImageData(String fileName) throws IOException;
}
