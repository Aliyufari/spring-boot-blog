package com.agicafe.blog.comments;

import com.agicafe.blog.post.Post;
import com.agicafe.blog.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public record Comment(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Integer id,

        @Column(name = "body", nullable = false)
        String body,

        @ManyToOne
        @JoinColumn(name = "post_id", nullable = false)
        Post post,

        @ManyToOne
        @JoinColumn(name = "author_id", nullable = false)
        User author
) {}
