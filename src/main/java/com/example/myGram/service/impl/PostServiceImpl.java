package com.example.myGram.service.impl;

import com.example.myGram.model.dto.PostResponse;
import com.example.myGram.model.dto.UpsertPostRequest;
import com.example.myGram.model.entity.Post;
import com.example.myGram.repository.PostRepository;
import com.example.myGram.repository.UserRepository;
import com.example.myGram.service.PostService;
import com.example.myGram.web.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Value("${app.upload.path}")
    private String uploadPath;

    @Override
    public List<PostResponse> findAll() {
        List<PostResponse> postResponses = postMapper.postListToResponseList(postRepository.findAll())
                .stream()
                .map(post -> {
                    try {
                        post.setBase64ImageData(getImageData(post.getFile()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    return post;
                })
                .collect(Collectors.toList());

        return postResponses;
    }

    @Override
    public PostResponse create(UpsertPostRequest post, MultipartFile file)  {
        Post tempPost = postMapper.requestToPost(post);

        if (file != null && !file.isEmpty()){
            File uploadDir = new File(uploadPath).getAbsoluteFile();

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            try {
                File destinationFile = new File(uploadDir, resultFilename);
                file.transferTo(destinationFile);
            } catch (IOException e) {
                throw new RuntimeException("Ошибка при сохранении файла", e);
            }

            tempPost.setFile(resultFilename);
        }

        PostResponse postResponse = postMapper.postToResponse(postRepository.save(tempPost));

        try {
            postResponse.setBase64ImageData(getImageData(tempPost.getFile()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return postMapper.postToResponse(postRepository.save(tempPost));
    }

    @Override
    public List<PostResponse> findPostsByUser(String username) {
        UUID userId = userRepository.findByUsername(username).get().getId();

        List<PostResponse> postResponses = postMapper.postListToResponseList(postRepository.getPostByUsername(userId))
                .stream()
                .map(post -> {
                    try {
                        post.setBase64ImageData(getImageData(post.getFile()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    return post;
                })
                .collect(Collectors.toList());

        return postResponses;
    }

    @Override
    public List<PostResponse> findPostsByUserId(UUID id) {

        List<PostResponse> postResponses = postMapper.postListToResponseList(postRepository.getPostByUsername(id))
                .stream()
                .map(post -> {
                    try {
                        post.setBase64ImageData(getImageData(post.getFile()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    return post;
                })
                .collect(Collectors.toList());

        return postResponses;
    }

    @Override
    public PostResponse findPostById(UUID id) {
        return postRepository.findById(id)
                .map(postMapper::postToResponse).get();
    }
    @Override
    public String getImageData(String fileName) throws IOException {
        Resource resource = new FileSystemResource(uploadPath + "/" + fileName);

        if (!resource.exists()) {
            return null;
        }

        try (InputStream inputStream = resource.getInputStream()) {
            byte[] imageData = StreamUtils.copyToByteArray(inputStream);
            return Base64.getEncoder().encodeToString(imageData);
        }
    }
}
