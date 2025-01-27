package com.agicafe.blog.post;

import com.agicafe.blog.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public record Post(
      Integer id,
      String title,
      String body,
      User author
) {}
