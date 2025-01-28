package com.agicafe.blog.comments;

import com.agicafe.blog.exceptions.CommentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getComments(){
        return commentRepository.findAll();
    }

    public Comment createComment(Comment comment){
        return commentRepository.save(comment);
    }

    public Optional<Comment> getComment(Integer id){
        return Optional.ofNullable(commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new));
    }

    public void updateComment(Comment commentUpdate){
        Optional<Comment> comment = commentRepository.findById(commentUpdate.id());
        if (comment.isPresent())
            commentRepository.save(commentUpdate);

        throw new CommentNotFoundException();
    }

    public void deleteComment(Integer id){
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent())
            commentRepository.deleteById(id);

        throw new CommentNotFoundException();
    }
}
