package com.thoughtworks.gradstepupspring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.gradstepupspring.controller.UserController;
import com.thoughtworks.gradstepupspring.domain.User;
import com.thoughtworks.gradstepupspring.repository.UserStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class UserControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = standaloneSetup(new UserController()).build();
        UserStorage.clear();
    }

    @Test
    void should_return_users_in_storage() throws Exception {
        UserStorage.addUser(new User(1, "xi xi"), new User(2, "hei hei"));
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("xi xi"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("hei hei"));
    }

    @Test
    void should_return_user_as_create_and_store_in_storage() throws Exception {
        User newUser = new User(3, "ga ga");

        int originalStorageSize = UserStorage.getUSERS().size();

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("ga ga"));

        int expectedSize = originalStorageSize + 1;
        int scaledSize = UserStorage.getUSERS().size();

        assertEquals(expectedSize, scaledSize);
    }
}
