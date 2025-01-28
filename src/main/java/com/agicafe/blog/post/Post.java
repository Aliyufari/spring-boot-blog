package com.agicafe.blog.post;

import com.agicafe.blog.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public record Post(
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      Integer id,

      @Column(name = "title", nullable = false)
      String title,

      @Column(name = "body", nullable = false)
      String body,

      @ManyToOne
      @JoinColumn(name = "author_id", nullable = false)
      User author
) {}
