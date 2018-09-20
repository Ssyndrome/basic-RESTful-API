package com.thoughtworks.gradstepupspring.repository;

import com.thoughtworks.gradstepupspring.domain.User;

import java.util.Collection;

public interface UserRepository {
    Collection<User> findUsers();

    User create(User user);

    User updateUser(int id, User user);

    void deleteUser(int id);
}
