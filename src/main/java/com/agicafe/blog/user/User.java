package com.agicafe.blog.user;

import com.agicafe.blog.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class User {
        @Id
        @UuidGenerator
        @Column(name = "id", unique = true, updatable = false)
        private UUID id;

        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "email", nullable = false)
        private String email;

        @Column(name = "password", nullable = false)
        private String password;

        @Enumerated(value = EnumType.STRING)
        private Role role;

        @OneToMany(mappedBy = "author")
        @JsonIgnore
        private List<Post> posts;

        public User(User user) {
        }
}
