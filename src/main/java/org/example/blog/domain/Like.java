package org.example.blog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Like.java
@Entity
@Table(name = "likes")
@Getter
@Setter
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


    // Getters and Setters
}
