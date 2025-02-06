package com.agicafe.blog.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdateCommentRequest(
        @NotBlank(message = "Body is required")
        String body
) {}
