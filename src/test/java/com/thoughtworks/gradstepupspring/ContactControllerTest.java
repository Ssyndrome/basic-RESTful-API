package com.thoughtworks.gradstepupspring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gradstepupspring.controller.ContactController;
import com.thoughtworks.gradstepupspring.domain.Contact;
import com.thoughtworks.gradstepupspring.domain.User;
import com.thoughtworks.gradstepupspring.repository.UserStorage;
import com.thoughtworks.gradstepupspring.repository.impl.ContactStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.useDefaultDateFormatsOnly;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ContactControllerTest {
    private MockMvc mockMvc;
    private User originalUser;
    private User anotherUser;
    private Contact originalContact;
    private Contact anotherContact;
    private Contact contactWithSameName;

    @BeforeEach
    void setUp() {
        UserStorage.clear();
        ContactStorage.clear();

        mockMvc = standaloneSetup(new ContactController()).build();
        originalUser = new User(9, "Syndrome");
        anotherUser = new User(10, "Sem");
        originalContact = new Contact(1, "Cartridge", 21, "male", 18872688331L);
        anotherContact = new Contact(1, "Car", 18, "male", 158L);
        contactWithSameName = new Contact(6, "Cartridge", 18, "male", 158L);

        UserStorage.addUser(originalUser);
    }

    @Test
    void should_succeed_create_one_contact_for_one_user() throws Exception {
        mockMvc.perform(post("/api/users/9/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(originalContact)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Cartridge"))
                .andExpect(jsonPath("$.age").value(21))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.tel").value(18872688331L));

        Contact addedContact = UserStorage.getUserById(9).getContacts().get(1);
        assertThat(addedContact.getId()).isEqualTo(originalContact.getId());
        assertThat(addedContact.getAge()).isEqualTo(originalContact.getAge());
        assertThat(addedContact.getName()).isEqualTo(originalContact.getName());
        assertThat(addedContact.getGender()).isEqualTo(originalContact.getGender());
        assertThat(addedContact.getTel()).isEqualTo(originalContact.getTel());
    }

    @Test
    void should_fail_to_create_one_repeated_id_contact() throws Exception{
        mockMvc.perform(post("/api/users/9/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(originalContact)))
                .andExpect(status().isCreated());
        UserStorage.addUser(anotherUser);

        mockMvc.perform(post("/api/users/10/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(originalContact)))
                .andExpect(status().isBadRequest());

        int anotherUserContactsAmount = UserStorage.getUserById(10).getContacts().size();
        assertThat(anotherUserContactsAmount).isEqualTo(0);

        UserStorage.clear();
    }

    @Test
    void should_get_all_valid_contacts_of_one_user() throws Exception {
        originalUser.setContact(originalContact);
        mockMvc.perform(get("/api/users/9/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.1.id").value(1))
                .andExpect(jsonPath("$.1.name").value("Cartridge"))
                .andExpect(jsonPath("$.1.age").value(21))
                .andExpect(jsonPath("$.1.gender").value("male"))
                .andExpect(jsonPath("$.1.tel").value(18872688331L));
    }

    @Test
    void should_succeed_update_one_contact_of_one_user_by_user_id() throws Exception {
        originalUser.setContact(originalContact);
        mockMvc.perform(put("/api/users/9/contacts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(anotherContact)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Car"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.tel").value(158L));

        Contact originalContactInRepository = ContactStorage.getContactById(originalContact.getId());
        assertThat(originalContactInRepository.getName()).isEqualTo("Car");
        assertThat(originalContactInRepository.getAge()).isEqualTo(18);
        assertThat(originalContactInRepository.getGender()).isEqualTo("male");
        assertThat(originalContactInRepository.getTel()).isEqualTo(158L);
    }

    @Test
    void should_succeed_delete_one_contact_by_id_and_user_id() throws Exception {
        originalUser.setContact(originalContact);
        ContactStorage.add(originalContact);

        mockMvc.perform(delete("/api/users/9/contacts/1"))
                .andExpect(status().isNoContent());

        assertThat(originalUser.getContacts().size()).isZero();
        assertThat(ContactStorage.getAllContacts().size()).isZero();
    }

    @Test
    void should_succeed_get_contact_by_user_name_and_contact_name() throws Exception {
        originalUser.setContact(originalContact);
        ContactStorage.add(originalContact);
        UserStorage.addUser(anotherUser);
        anotherUser.setContact(contactWithSameName);
        ContactStorage.add(contactWithSameName);

        mockMvc.perform(get("/api/users/contacts")
                .param("userName", "Syndrome")
                .param("contactName", "Cartridge"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Cartridge"))
                .andExpect(jsonPath("$.age").value(21))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.tel").value(18872688331L));
    }
}
