package com.example.myGram.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "comment")
@Builder
public class Comment {
    @Id
    private UUID id;

    @Column(name = "date_create")
    private Instant dateCreate;
    @Column(name = "date_update")
    private Instant dateUpdate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;
}
