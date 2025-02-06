package com.agicafe.blog.comments;

import com.agicafe.blog.payload.ApiResponse;
import com.agicafe.blog.post.Post;
import com.agicafe.blog.post.PostService;
import com.agicafe.blog.dtos.StoreCommentRequest;
import com.agicafe.blog.dtos.UpdateCommentRequest;
import com.agicafe.blog.user.User;
import com.agicafe.blog.user.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/comments")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;

    public CommentController(final CommentService commentService, final UserService userService, final PostService postService) {
        this.commentService = commentService;
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping()
    private ResponseEntity<ApiResponse<List<Comment>>> index(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
            )
    {
        Page<Comment> comments = commentService.getComments(page, size);
        ApiResponse<List<Comment>> response = new ApiResponse<>(true, "Comments fetched successfully", "comments", comments);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    private ResponseEntity<ApiResponse<Comment>> store(@Valid @RequestBody StoreCommentRequest request, UriComponentsBuilder ucb){
        Optional<User> optionalUser = userService.getUser(request.author_id());
        Optional<Post> optionalPost = postService.getPost(request.post_id());

        if (optionalUser.isEmpty()){
            ApiResponse<User> response = new ApiResponse<>(false, "Invalid User", null, null);
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        if (optionalPost.isEmpty()){
            ApiResponse<Comment> response = new ApiResponse<>(false, "Invalid Post", null, null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Comment newComment = new Comment();
        newComment.setBody(request.body());
        newComment.setPost(optionalPost.get());
        newComment.setAuthor(optionalUser.get());

        Comment comment = commentService.createComment(newComment);

        URI locationOfNewComment = ucb.path("/comments/{id}")
                .buildAndExpand(comment.getId())
                .toUri();

        ApiResponse<Comment> response = new ApiResponse<>(true, "Comment created successfully", "comment", comment);
        return ResponseEntity.created(locationOfNewComment).body(response);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ApiResponse<Comment>> show(@PathVariable UUID id) {
        return commentService.getComment(id)
                .map(comment -> {
                    ApiResponse<Comment> response = new ApiResponse<>(true, "Comment fetched successfully", "comment", comment);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    ApiResponse<Comment> response = new ApiResponse<>(false, "Comment Not Found", null, null);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    @PutMapping("/{id}")
    private ResponseEntity<ApiResponse<Comment>> update(@Valid @RequestBody UpdateCommentRequest request, @PathVariable UUID id){
        Optional<Comment> optionalComment = Optional.ofNullable(commentService.getComment(id)
                .orElseThrow(() -> new RuntimeException("Comment Not Found")));

        if (optionalComment.isEmpty()){
            ApiResponse<Comment> response = new ApiResponse<>(false, "Comment Not Found", null, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Comment comment = optionalComment.get();
        comment.setBody(request.body());

        Comment updatedComment = commentService.updateComment(comment);
        ApiResponse<Comment> response = new ApiResponse<>(true, "Comment updated successfully", "comment", updatedComment);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<ApiResponse<Void>> destroy(@PathVariable UUID id){
        if (commentService.getComment(id).isEmpty()){
            ApiResponse<Void> response = new ApiResponse<>(false, "Comment Not Found", null, null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        commentService.deleteComment(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Comment deleted successfully", null, null);
        return ResponseEntity.ok(response);
    }
}
