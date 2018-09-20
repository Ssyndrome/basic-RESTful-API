package com.thoughtworks.gradstepupspring.controller;

import com.thoughtworks.gradstepupspring.domain.Contact;
import com.thoughtworks.gradstepupspring.repository.ContactRepository;
import com.thoughtworks.gradstepupspring.repository.UserStorage;
import com.thoughtworks.gradstepupspring.repository.impl.ContactRepositoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    private ContactRepository contactRepository = new ContactRepositoryImpl();

    @PostMapping("/api/users/{userId}/contacts")
    public ResponseEntity createContact(@PathVariable int userId,
                                        @RequestBody Contact contact) {
        boolean canBeAddedToUser = contactRepository.createContactForAnUser(UserStorage.getUserById(userId), contact);
        if (canBeAddedToUser) {
            return new ResponseEntity<>(
                    contact,
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
