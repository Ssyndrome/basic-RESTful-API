package com.thoughtworks.gradstepupspring.repository;

import com.thoughtworks.gradstepupspring.domain.Contact;
import com.thoughtworks.gradstepupspring.domain.User;

import java.util.Map;

public interface ContactRepository {
    boolean createContactForAnUser(User user, Contact contact);

    Map<Integer, Contact> getContactsByUserId(int userId);
}
