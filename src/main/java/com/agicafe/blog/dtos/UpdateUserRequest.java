package com.agicafe.blog.dtos;

import com.agicafe.blog.user.Role;
import com.agicafe.blog.validations.Unique;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Unique(message = "Email already taken", fieldName = "email")
        String email,

        @Nullable
        Role role
) {}
