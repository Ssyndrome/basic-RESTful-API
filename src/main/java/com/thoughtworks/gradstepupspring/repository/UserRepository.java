package com.thoughtworks.gradstepupspring.repository;

import com.thoughtworks.gradstepupspring.domain.User;

import java.util.Collection;

public interface UserRepository {
    Collection<User> findUsers();

    User create(User user);

    boolean checkIfValidUser();
}
