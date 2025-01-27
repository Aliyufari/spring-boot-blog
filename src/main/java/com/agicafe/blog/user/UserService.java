package com.agicafe.blog.user;

import com.agicafe.blog.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllusers(){
        return userRepository.findAll();
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public Optional<User> getUser(Integer id){
        return userRepository.findById(id);
    }

    public void updateUser(User user){
        if (userRepository.existsById(user.id()))
            userRepository.save(user);
        else
            throw new UserNotFoundException("User Not Found");
    }

    public void deleteUser(Integer id){
        if (userRepository.existsById(id))
            userRepository.deleteById(id);
        else
            throw new UserNotFoundException("User Not Found");
    }
}
