package com.thoughtworks.gradstepupspring.repository.impl;

import com.thoughtworks.gradstepupspring.domain.User;
import com.thoughtworks.gradstepupspring.repository.UserRepository;
import com.thoughtworks.gradstepupspring.repository.UserStorage;

import java.util.Collection;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public Collection<User> findUsers() {
        return UserStorage.getUSERS();
    }

    @Override
    public User create(User user) {
        UserStorage.addUser(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        UserStorage.getUserById(user.getId()).setName(user.getName());
        return user;
    }
}
