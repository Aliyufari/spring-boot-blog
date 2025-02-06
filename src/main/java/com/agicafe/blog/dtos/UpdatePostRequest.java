package com.agicafe.blog.dtos;

import com.agicafe.blog.validations.Unique;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UniqueElements;

public record UpdatePostRequest(
        @NotBlank(message = "Title is required")
        @Unique(message = "Post title already exists", fieldName = "title")
        String title,

        @NotBlank(message = "Body is required")
        String body
) {}
