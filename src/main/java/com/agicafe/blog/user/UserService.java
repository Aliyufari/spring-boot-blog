package com.agicafe.blog.user;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<User> getUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size, Sort.by("name")));
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
        if (!userRepository.existsById(user.getId())){
            throw new RuntimeException("User Not Found");
        }
        return userRepository.save(user);
    }

    public void deleteUser(UUID id){
        if (!userRepository.existsById(id)){
            throw new RuntimeException("User Not Found");
        }
        userRepository.deleteById(id);
    }
}
