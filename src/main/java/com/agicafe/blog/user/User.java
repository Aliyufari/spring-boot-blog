package com.agicafe.blog.user;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public record User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Integer id,

        @Column(name = "name", nullable = false)
        String name,

        @Column(name = "email", nullable = false)
        String email,

        @Column(name = "password", nullable = false)
        String password,

        @Enumerated(value = EnumType.STRING)
        Role role
) {}
