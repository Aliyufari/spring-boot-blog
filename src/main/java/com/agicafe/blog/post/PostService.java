package com.agicafe.blog.post;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(rollbackOn = Exception.class)
public class PostService {

    private final PostRepository postRepository;

    public PostService(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<Post> getPosts(int page, int size){
        return postRepository.findAll(PageRequest.of(page, size));
    }

    public Post createPost(Post post){
        return postRepository.save(post);
    }

    public Optional<Post> getPost(UUID id){
        return Optional.of(postRepository.findById(id))
                .orElseThrow(()-> new RuntimeException("Post Not Found!"));
    }

    public Post updatePost(Post post){
        if (postRepository.existsById(post.getId())){
           return postRepository.save(post);
        }

        throw  new RuntimeException("Post Not Found!");
    }

    public void deletePost(UUID id){
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Post Not Found!");
        }

        postRepository.deleteById(id);
    }
}
