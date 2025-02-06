package com.agicafe.blog.dtos;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UniqueElements;

public record UpdatePostRequest(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Body is required")
        String body
) {}
