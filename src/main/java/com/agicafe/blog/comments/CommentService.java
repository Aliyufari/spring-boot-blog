package com.agicafe.blog.comments;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(rollbackOn = Exception.class)
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Page<Comment> getComments(int page, int size){
        return commentRepository.findAll(PageRequest.of(page, size));
    }

    public Comment createComment(Comment comment){
        return commentRepository.save(comment);
    }

    public Optional<Comment> getComment(UUID id){
        return Optional.of(commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment Not Found")));
    }

    public Comment updateComment(Comment commentUpdate){
        if (commentRepository.existsById(commentUpdate.getId())) {
            return commentRepository.save(commentUpdate);
        }
        throw new RuntimeException("Comment Not Found");
    }

    public void deleteComment(UUID id){
        if (!commentRepository.existsById(id)) {
            throw new RuntimeException("Comment Not Found");
        }
        commentRepository.deleteById(id);
    }
}
