package com.agicafe.blog.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record StoreCommentRequest(
        @NotBlank(message = "Body is required")
        String body,

        @NotNull(message = "Author ID is required")
        UUID author_id,

        @NotNull(message = "Post id is required")
        UUID post_id
) {}

