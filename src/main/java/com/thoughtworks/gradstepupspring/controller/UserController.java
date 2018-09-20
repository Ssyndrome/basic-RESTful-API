package com.thoughtworks.gradstepupspring.controller;

import com.thoughtworks.gradstepupspring.domain.User;
import com.thoughtworks.gradstepupspring.repository.UserRepository;
import com.thoughtworks.gradstepupspring.repository.impl.UserRepositoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class UserController {

    private UserRepository userRepository = new UserRepositoryImpl();

    @GetMapping("/api/users")
    public Collection<User> queryUsers() {
        return userRepository.findUsers();
    }

}
