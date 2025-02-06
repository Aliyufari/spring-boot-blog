package com.agicafe.blog.validations;

import com.agicafe.blog.post.PostRepository;
import com.agicafe.blog.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UniqueFieldValidator implements ConstraintValidator<Unique, String> {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private String fieldName;

    public UniqueFieldValidator(final UserRepository userRepository, final PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext constraintValidatorContext) {

        if (field == null || field.trim().isEmpty()) {
            return true;
        }

        if ("email".equalsIgnoreCase(fieldName)) {
            return !userRepository.existsByEmail(field);
        } else if ("title".equalsIgnoreCase(fieldName)) {
            return !postRepository.existsByTitle(field);
        }

        return true;
    }
}
