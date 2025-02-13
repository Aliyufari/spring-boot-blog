package com.agicafe.blog.comments;

import com.agicafe.blog.post.Post;
import com.agicafe.blog.user.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment{
        @Id
        @UuidGenerator
        @Column(name = "id", unique = true, updatable = false)
        private UUID id;

        @Column(name = "body", nullable = false)
        private String body;

        @ManyToOne
        @JoinColumn(name = "post_id", nullable = false)
        private Post post;

        @Column(name = "created_at", nullable = false, updatable = false)
        @CreationTimestamp
        private LocalDateTime createdAt;

        @ManyToOne
        @JoinColumn(name = "author_id", nullable = false)
        private User author;
}
