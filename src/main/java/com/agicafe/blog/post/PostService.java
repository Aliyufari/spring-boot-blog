package com.agicafe.blog.post;

import com.agicafe.blog.exceptions.PostNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts(){
        return postRepository.findAll();
    }

    public Post createPost(Post post){
        return postRepository.save(post);
    }

    public Optional<Post> getPost(Integer id){
        return Optional.ofNullable(postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new));
    }

    public void updatePost(Post post){
        if (postRepository.existsById(post.id())){
            postRepository.save(post);
        }

        throw  new PostNotFoundException("Post Not Found!");
    }

    public void deletePost(Integer id){
        if (postRepository.existsById(id)){
            postRepository.deleteById(id);
        }

        throw  new PostNotFoundException("Post Not Found!");
    }
}
