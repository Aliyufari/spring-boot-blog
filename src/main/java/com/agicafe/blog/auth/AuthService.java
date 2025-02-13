package com.agicafe.blog.auth;

import com.agicafe.blog.dtos.LoginRequest;
import com.agicafe.blog.dtos.RegisterRequest;
import com.agicafe.blog.user.Role;
import com.agicafe.blog.user.User;
import com.agicafe.blog.user.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String signup(@Valid RegisterRequest user) {
        User newUser = new User();
        newUser.setName(user.name());
        newUser.setEmail(user.email());
        newUser.setPassword(passwordEncoder.encode(user.password()));
        newUser.setRole(Role.USER);

        User registeredUser = userRepository.save(newUser);
        return jwtService.generateToken(registeredUser);
    }

    public String signin(@Valid LoginRequest user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.email(),
                        user.password()
                )
        );

        User authUser = userRepository.findByEmail(user.email())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        return jwtService.generateToken(authUser);
    }
}
