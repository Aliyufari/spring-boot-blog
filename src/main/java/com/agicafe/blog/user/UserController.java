package com.agicafe.blog.user;

import com.agicafe.blog.payload.ApiResponse;
import com.agicafe.blog.dtos.StoreUserRequest;
import com.agicafe.blog.dtos.UpdateUserRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<User>>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){
        Page<User> pages = userService.getUsers(page, size);

        ApiResponse<List<User>> response = new ApiResponse<>(true, "Users fetched successfully", "users", pages);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping()
    private ResponseEntity<ApiResponse<User>> store(@Valid @RequestBody StoreUserRequest request, UriComponentsBuilder ucb){
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
//        user.setPassword(passwordEncorder.encode(request.password()));
        user.setPassword(request.password());
        user.setRole(request.role() != null ? request.role() : Role.USER);

        User savedUser = userService.createUser(user);
        URI locationOfNewUser = ucb.path("/users/{id}").buildAndExpand(savedUser.getId()).toUri();
        ApiResponse<User> response = new ApiResponse<>(true, "User created successfully", "user", savedUser);
        return ResponseEntity.created(locationOfNewUser).body(response);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ApiResponse<User>> show(@PathVariable UUID id){
        return userService.getUser(id)
                .map(user -> {
                    ApiResponse<User> response = new ApiResponse<>(true, "User fetched successfully", "user", user);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponse<User> response = new ApiResponse<>(false, "User Not Found", "user", null);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    @PutMapping("/{id}")
    private ResponseEntity<ApiResponse<User>> update(@Valid @RequestBody UpdateUserRequest request, @PathVariable UUID id){
        Optional<User> optionalUser = userService.getUser(id);
        if (optionalUser.isEmpty()){
            ApiResponse<User> response = new ApiResponse<>(false, "User not Found", null, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        User user = optionalUser.get();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setRole(request.role());

        User updatedUser = userService.updateUser(user);
        ApiResponse<User> response = new ApiResponse<>(true, "User updated successfully", "user", updatedUser);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<ApiResponse<Void>> destroy(@PathVariable UUID id){
        if (userService.getUser(id).isEmpty()){
            ApiResponse<Void> response = new ApiResponse<>(false, "User not Found", null, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        userService.deleteUser(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "User deleted successfully", null, null);
        return ResponseEntity.ok(response);
    }
}
