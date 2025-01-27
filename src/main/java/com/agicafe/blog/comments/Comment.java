package com.agicafe.blog.comments;

import com.agicafe.blog.post.Post;
import com.agicafe.blog.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "comments")
public record Comment(
        Integer id,
        String body,
        Post post,
        User author
) {}
