package com.agicafe.blog.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    private Iterable<User> index(){
        return userService.getAllusers();
    }

    @PostMapping("/")
    private ResponseEntity<Void> store(@RequestBody User user, UriComponentsBuilder ucb){
        User newUser = new User(null, user.name(), user.email(), user.password());
        User savedUser = userService.createUser(newUser);
        URI locationOfNewUser = ucb.path("/users/{id}")
                .buildAndExpand(savedUser.id())
                .toUri();
        return ResponseEntity.created(locationOfNewUser).build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<User> show(@PathVariable Integer id){
        Optional<User> user = userService.getUser(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    private ResponseEntity<Void> update(@PathVariable Integer id, @RequestBody User userUpdate){
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()){
            User updatedUser = new User(userUpdate.id(), userUpdate.name(), userUpdate.email(), userUpdate.password());
            userService.updateUser(updatedUser);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> destroy(@PathVariable Integer id){
        Optional<User> user = userService.getUser(id);
        if (user.isPresent()){
            userService.deleteUser(user.get().id());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
