package com.thoughtworks.gradstepupspring.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class User {
    private Integer id;
    private String name;
    private Map<Integer, Contact> contacts = new HashMap<>();

    public User() {
    }

    public User(Integer originalId, String originalName) {
        this.id = originalId;
        this.name = originalName;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<Integer, Contact> getContacts() {
        return contacts;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(Contact... contacts) {
        Arrays.stream(contacts).forEach(contact -> this.contacts.put(contact.getId(), contact));
    }
}
