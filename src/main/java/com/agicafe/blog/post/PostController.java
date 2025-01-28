package com.agicafe.blog.post;

import com.agicafe.blog.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(final PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    private Iterable<Post> index(){
        return postService.getPosts();
    }

    @PostMapping("/")
    private ResponseEntity<Void> store(@RequestBody Post post, UriComponentsBuilder ucb){
        Post newPost = new Post(null, post.title(), post.body(), post.author());
        Post savedPost = postService.createPost(newPost);
        URI locationOfNewPost = ucb.path("/posts/{id}").buildAndExpand(savedPost.id()).toUri();
        return ResponseEntity.created(locationOfNewPost).build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<Post> show(@PathVariable Integer id){
        Optional<Post> post = postService.getPost(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody Post postUpdate){
        Optional<Post> post = postService.getPost(id);
        if (post.isPresent()){
            postService.updatePost(postUpdate);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> destroy(Integer id){
        Optional<Post> post = postService.getPost(id);
        if (post.isPresent()){
            postService.deletePost(post.get().id());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
