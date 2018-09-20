package com.thoughtworks.gradstepupspring.repository;

import com.thoughtworks.gradstepupspring.domain.User;

import java.util.*;

public class UserStorage {
    private final static Map<Integer, User> USERS = new HashMap<>();

    static {
        USERS.put(1, new User(1,"xi xi"));
        USERS.put(2, new User(2, "hei hei"));
    }

    public static Collection<User> getUSERS() {
        return new ArrayList<>(USERS.values());
    }


    public static void addUser(User... users) {
        Arrays.stream(users).forEach(user -> USERS.put(user.getId(), user));
    }

    public static void clear() {
        USERS.clear();
    }

    public static User getUserById(int id) {
        return USERS.get(id);
    }
}
