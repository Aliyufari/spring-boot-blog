package com.agicafe.blog.post;

import com.agicafe.blog.payload.ApiResponse;
import com.agicafe.blog.dtos.StorePostRequest;
import com.agicafe.blog.dtos.UpdatePostRequest;
import com.agicafe.blog.user.User;
import com.agicafe.blog.user.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    public PostController(final PostService postService, final UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping()
    private ResponseEntity<ApiResponse<List<Post>>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){
        Page<Post> posts = postService.getPosts(page, size);
        ApiResponse<List<Post>> response = new ApiResponse<>(true, "Posts fetched successfully", "posts", posts);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping()
    private ResponseEntity<ApiResponse<Post>> store(@Valid @RequestBody StorePostRequest request, UriComponentsBuilder ucb){
        Optional<User> author = userService.getUser(request.userId());

        if (author.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Post newPost = new Post();
        newPost.setTitle(request.title());
        newPost.setBody(request.body());
        newPost.setAuthor(author.get());

        Post savedPost = postService.createPost(newPost);
        URI locationOfNewPost = ucb.path("/posts/{id}").buildAndExpand(savedPost.getId()).toUri();
        ApiResponse<Post> response = new ApiResponse<>(true, "Post created successful", "post", savedPost);
        return ResponseEntity.created(locationOfNewPost).body(response);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ApiResponse<Post>> show(@PathVariable UUID id){
        return postService.getPost(id)
                .map(post -> {
                    ApiResponse<Post> response = new ApiResponse<>(true, "Post fetched successfully", "post", post);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponse<Post> response = new ApiResponse<>(false, "Post Not Found", null, null);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    @PutMapping("/{id}")
    private ResponseEntity<ApiResponse<Post>> update(@Valid @RequestBody UpdatePostRequest request, @PathVariable UUID id){
        Optional<Post> optionalPost = postService.getPost(id);
        if (optionalPost.isEmpty()){
            ApiResponse<Post> response = new ApiResponse<>(false, "Post Not Found", null, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Post post = optionalPost.get();
        post.setTitle(request.title());
        post.setBody(request.body());

        Post updatedPost = postService.updatePost(post);
        ApiResponse<Post> response = new ApiResponse<>(true, "Post updated successfully", "post", updatedPost);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<ApiResponse<Void>> destroy(@PathVariable UUID id){
        if (postService.getPost(id).isEmpty()){
            ApiResponse<Void> response = new ApiResponse<>(false, "Post Not Found", null, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        postService.deletePost(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Post deleted successfully", null, null);
        return ResponseEntity.ok(response);
    }
}
