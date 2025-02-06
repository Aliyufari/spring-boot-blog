package com.agicafe.blog.dtos;

import com.agicafe.blog.user.Role;
import com.agicafe.blog.validations.Unique;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record StoreUserRequest(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Unique(message = "Email already taken", fieldName = "email")
        String email,

        @NotBlank(message = "Password is required")
        @Length(min = 6, message = "Password must be at least 6 characters long")
        String password,

        @Nullable
        Role role
) {}
