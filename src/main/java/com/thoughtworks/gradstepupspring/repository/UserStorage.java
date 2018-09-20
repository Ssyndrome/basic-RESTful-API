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
        USERS.forEach((id, user) -> user.getContacts().clear());
    }

    public static User getUserById(int id) {
        return USERS.get(id);
    }

    public static void deleteUserById(int id) {
        USERS.remove(id);
    }

    public static void deleteUserContactById(int userId, int contactId) {
        getUserById(userId).getContacts().remove(contactId);
    }

    public static User findUserByName(String userName) {
        return USERS.values().stream().filter(user -> user.getName().equals(userName)).findFirst().get();
    }
}
