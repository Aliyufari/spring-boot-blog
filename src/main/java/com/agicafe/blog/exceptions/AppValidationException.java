package com.agicafe.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@RestControllerAdvice
public class AppValidationException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public LinkedHashMap<String, Object> handleValidation (MethodArgumentNotValidException exception){

        LinkedHashMap<String, Object> response = new LinkedHashMap<>();
        response.put("status", false);

        List<HashMap<String, String>> errors = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            HashMap<String, String> errorDetails = new HashMap<>();
            errorDetails.put(error.getField(), error.getDefaultMessage());
            errors.add(errorDetails);
        });

        response.put("errors", errors);

        return response;
    }
}
