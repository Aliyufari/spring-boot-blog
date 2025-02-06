package com.agicafe.blog.user;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(rollbackOn = Exception.class)
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User createUser(User user){
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUser(UUID id){
        return Optional.of(userRepository.findById(id))
                .orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    public User updateUser(User user){
        if (userRepository.existsById(user.getId()))
           return userRepository.save(user);
        else
            throw new RuntimeException("User Not Found");
    }

    public void deleteUser(UUID id){
        if (userRepository.existsById(id))
            userRepository.deleteById(id);
        else
            throw new RuntimeException("User Not Found");
    }
}
