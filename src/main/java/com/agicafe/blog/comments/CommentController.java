package com.agicafe.blog.comments;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/")
    private Iterable<Comment> index(){
        return commentService.getComments();
    }

    @PostMapping("/")
    private ResponseEntity<Void> store(@RequestBody Comment comment, UriComponentsBuilder ucb){
        Comment newComment = new Comment(null, comment.body(), comment.post(), comment.author());
        Comment savedComment = commentService.createComment(newComment);

        URI locationOfNewComment = ucb.path("/comments/{id}")
                .buildAndExpand(savedComment.id())
                .toUri();

        return ResponseEntity.created(locationOfNewComment).build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<Optional<Comment>> show(@PathVariable Integer id) {
        Optional<Comment> comment = commentService.getComment(id);

        if (comment.isPresent()){
            return ResponseEntity.ok(comment);
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    private ResponseEntity<Void> update(@PathVariable Integer id, Comment commentUpdate){
        Optional<Comment> comment = commentService.getComment(id);

        if (comment.isPresent()){
            commentService.updateComment(commentUpdate);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> destroy(@PathVariable Integer id){
        Optional<Comment> comment = commentService.getComment(id);

        if (comment.isPresent()){
            commentService.deleteComment(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
