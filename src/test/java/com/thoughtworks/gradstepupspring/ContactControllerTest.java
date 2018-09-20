package com.thoughtworks.gradstepupspring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gradstepupspring.controller.ContactController;
import com.thoughtworks.gradstepupspring.domain.Contact;
import com.thoughtworks.gradstepupspring.domain.User;
import com.thoughtworks.gradstepupspring.repository.UserStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.useDefaultDateFormatsOnly;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ContactControllerTest {
    private MockMvc mockMvc;
    private User originalUser;
    private Contact originalContact;

    @BeforeEach
    void setUp() {
        UserStorage.clear();

        mockMvc = standaloneSetup(new ContactController()).build();
        originalUser = new User(9, "Syndrome");
        originalContact = new Contact(1, "Cartridge", 21, "male", 18872688331L);
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
}
