package com.example.myGram.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "app_user")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;

    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    private Short age;
    private String avatar;
    private String city;
    private String country;
    private String status;

    @Column(name = "create_at")
    private Instant createAt;
    @Column(name = "online_at")
    private Instant onlineAt;

    @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "roles", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<RoleType> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();
}
