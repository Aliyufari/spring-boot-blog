package com.agicafe.blog.post;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    private List<Post> postsLists(){
        return postRepository.findAll();
    }

    private Post createPost(Post post){
        return postRepository.save(post);
    }


}
