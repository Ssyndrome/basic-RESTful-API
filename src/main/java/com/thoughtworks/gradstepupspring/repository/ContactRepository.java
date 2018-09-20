package com.thoughtworks.gradstepupspring.repository;

import com.thoughtworks.gradstepupspring.domain.Contact;
import com.thoughtworks.gradstepupspring.domain.User;

public interface ContactRepository {
    boolean createContactForAnUser(User user, Contact contact);
}
