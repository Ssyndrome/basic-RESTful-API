package com.thoughtworks.gradstepupspring.repository.impl;

import com.thoughtworks.gradstepupspring.domain.Contact;
import com.thoughtworks.gradstepupspring.domain.User;
import com.thoughtworks.gradstepupspring.repository.ContactRepository;
import com.thoughtworks.gradstepupspring.repository.UserStorage;

import java.util.Map;

public class ContactRepositoryImpl implements ContactRepository {

    @Override
    public boolean createContactForAnUser(User user, Contact contact) {
        boolean canAdd = ContactStorage.add(contact);
        if (canAdd) {
            user.setContact(contact);
        }
        return canAdd;
    }

    @Override
    public Map<Integer, Contact> getContactsByUserId(int userId) {
        return UserStorage.getUserById(userId).getContacts();
    }
}
