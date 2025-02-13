package com.agicafe.blog.auth;

import com.agicafe.blog.dtos.LoginRequest;
import com.agicafe.blog.dtos.RegisterRequest;
import com.agicafe.blog.payload.ApiResponse;
import com.agicafe.blog.user.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {
    private final AuthService authServise;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/api/register")
    private ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody RegisterRequest request){
        var user = authServise.signup(request);
        ApiResponse<User> response = new ApiResponse<>(true, "Registered successfully", "token", user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/login")
    private ResponseEntity<ApiResponse<?>> login(@Valid @RequestBody LoginRequest request){
        var user = authServise.signin(request);
        ApiResponse<User> response = new ApiResponse<>(true, "Logged In successfully", "token", user);
        return ResponseEntity.ok(response);
    }
}
