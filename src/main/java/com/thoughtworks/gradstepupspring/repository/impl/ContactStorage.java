package com.thoughtworks.gradstepupspring.repository.impl;

import com.thoughtworks.gradstepupspring.domain.Contact;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ContactStorage {
    private static final Map<Integer, Contact> CONTACTS = new HashMap<>();

    public static boolean add(Contact... contacts) {
        if (Arrays.stream(contacts).anyMatch(contact -> CONTACTS.containsKey(contact.getId()))) {
            return false;
        }
        Arrays.stream(contacts).forEach(contact -> CONTACTS.put(contact.getId(), contact));
        return true;
    }

    public static void coverContactById(Contact contact) {
        CONTACTS.put(contact.getId(), contact);
    }

    public static Contact getContactById(Integer id) {
        return CONTACTS.get(id);
    }

    public static void clear() {
        CONTACTS.clear();
    }
}
