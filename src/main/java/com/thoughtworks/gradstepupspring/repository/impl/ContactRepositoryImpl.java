package com.thoughtworks.gradstepupspring.repository.impl;

import com.thoughtworks.gradstepupspring.domain.Contact;
import com.thoughtworks.gradstepupspring.domain.User;
import com.thoughtworks.gradstepupspring.repository.ContactRepository;
import com.thoughtworks.gradstepupspring.repository.UserStorage;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public Contact updateOneContact(int userId, Contact contact) {
        UserStorage.getUserById(userId).getContacts().get(contact.getId()).updateField(contact);
        ContactStorage.coverContactById(contact);
        return contact;
    }

    @Override
    public void deleteOneContact(int userId, int contactId) {
        UserStorage.deleteUserContactById(userId, contactId);
        ContactStorage.deleteConcatById(contactId);
    }

    @Override
    public Contact queryContactWithUserNameAndContactName(String userName, String contactName) {
        List<Contact> results = new ArrayList<>();
        UserStorage.findUserByName(userName).getContacts().forEach(
                (id, contact) -> {
                    if (contact.getName().equals(contactName)) {
                        results.add(contact);
                    }
                });
        return results.get(0);
    }
}
