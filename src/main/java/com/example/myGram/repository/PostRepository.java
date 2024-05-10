package com.example.myGram.repository;

import com.example.myGram.model.dto.PostResponse;
import com.example.myGram.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    @Query(value = "Select * From myproject_schema.post o Where o.username = :user_id", nativeQuery = true)
    List<Post> getPostByUsername(@Param("user_id")UUID userid);
}
