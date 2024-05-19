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
                .map(this::convertPostToPostResponseWithImageData)
                .collect(Collectors.toList());

        return postResponses;
    }

    @Override
    public PostResponse create(UpsertPostRequest post, MultipartFile file) {
        Post tempPost = postMapper.requestToPost(post);

        if (file != null && !file.isEmpty()) {
            String resultFilename = saveFile(file);
            tempPost.setFile(resultFilename);
        }

        Post savedPost = postRepository.save(tempPost);
        PostResponse postResponse = postMapper.postToResponse(savedPost);

        if (savedPost.getFile() != null) {
            try {
                postResponse.setBase64ImageData(getImageData(savedPost.getFile()));
            } catch (IOException e) {
                throw new RuntimeException("Ошибка при получении данных изображения", e);
            }
        }

        return postResponse;
    }

    @Override
    public List<PostResponse> findPostsByUser(String username) {
        UUID userId = userRepository.findByUsername(username).get().getId();

        List<PostResponse> postResponses = postMapper.postListToResponseList(postRepository.getPostByUsername(userId))
                .stream()
                .map(this::convertPostToPostResponseWithImageData)
                .collect(Collectors.toList());

        return postResponses;
    }

    @Override
    public List<PostResponse> findPostsByUserId(UUID id) {

        List<PostResponse> postResponses = postMapper.postListToResponseList(postRepository.getPostByUsername(id))
                .stream()
                .map(this::convertPostToPostResponseWithImageData)
                .collect(Collectors.toList());

        return postResponses;
    }

    @Override
    public PostResponse findPostById(UUID id) {
        return postRepository.findById(id)
                .map(postMapper::postToResponse).map(this::convertPostToPostResponseWithImageData).get();
    }

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

    private PostResponse convertPostToPostResponseWithImageData(PostResponse post) {
        try {
            String base64ImageData = getImageData(post.getFile());
            post.setBase64ImageData(base64ImageData);
        } catch (IOException e) {
            throw new RuntimeException("Error processing image data for post: " + post.getId(), e);
        }
        return post;
    }

    private String saveFile(MultipartFile file) {
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

        return resultFilename;
    }
}
