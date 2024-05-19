package com.example.myGram.model.dto;

import com.example.myGram.model.entity.Comment;
import com.example.myGram.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

    private UUID id;
    private String title;
    private Instant dateCreate;
    private Instant dateUpdate;

    private String base64ImageData;

    private String file;
}
