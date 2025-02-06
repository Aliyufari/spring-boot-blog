package com.agicafe.blog.post;

import com.agicafe.blog.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post{
      @Id
      @UuidGenerator
      @Column(name = "id", unique = true, updatable = false)
      private UUID id;

      @Column(name = "title", nullable = false)
      private String title;

      @Column(name = "body", nullable = false)
      private String body;

      @ManyToOne
      @JoinColumn(name = "author_id", nullable = false)
      @JsonBackReference
      private User author;
}
