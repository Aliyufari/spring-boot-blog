package com.agicafe.blog.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record StorePostRequest(
        @NotBlank(message = "Title is required")
//        @UniqueElements(message = "Title already exists")
        String title,

        @NotBlank(message = "Body is required")
        String body,

        @NotNull(message = "Author is required")
        UUID userId
) {}
