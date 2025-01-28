package com.agicafe.blog.exceptions;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException() {
        super("Comment Not Found");
    }

    public CommentNotFoundException(String message) {
        super(message);
    }
}
