package com.agicafe.blog.user;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public record User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Integer id,

        @Column(nullable = false)
        String name,

        @Column(nullable = false)
        String email,

        @Column(nullable = false)
        String password
) {}
