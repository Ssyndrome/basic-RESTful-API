package com.thoughtworks.gradstepupspring.controller;

import com.thoughtworks.gradstepupspring.domain.User;
import com.thoughtworks.gradstepupspring.repository.UserRepository;
import com.thoughtworks.gradstepupspring.repository.impl.UserRepositoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class UserController {

    private UserRepository userRepository = new UserRepositoryImpl();

    @GetMapping("/api/users")
    public Collection<User> queryUsers() {
        return userRepository.findUsers();
    }

    @PostMapping("/api/users")
    public ResponseEntity addUser(@RequestBody User user) {
        userRepository.create(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity updateUser(@PathVariable int id, @RequestBody User user) {
        return new ResponseEntity<>(userRepository.updateUser(id, user), HttpStatus.OK);
    }

    @DeleteMapping("/api/users/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {
        userRepository.deleteUser(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
