package com.agicafe.blog.dtos;

import com.agicafe.blog.validations.Unique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record StorePostRequest(
        @NotBlank(message = "Title is required")
        @Unique(message = "Post title already exists", fieldName = "title")
        String title,

        @NotBlank(message = "Body is required")
        String body,

        @NotNull(message = "Author is required")
        UUID userId
) {}
