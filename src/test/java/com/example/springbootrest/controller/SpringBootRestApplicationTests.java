package com.example.springbootrest.controller;

import org.application.SpringBootRestApplication;
import org.application.model.user.UserData;
import org.application.service.UserStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(classes = {SpringBootRestApplication.class})
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class SpringBootRestApplicationTests {

    @Autowired
    private UserStorageService userStorageService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DirtiesContext
    void getSavedUserApi() throws Exception {

        UserData userDataToSave = new UserData(null, "PreSavedUserName", "presaved_user_email@gmail.com");

        userStorageService.saveUser(userDataToSave);

        mockMvc.perform(get("/userdata/"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(containsString(userDataToSave.getName())))
                .andExpect(content().string(containsString(userDataToSave.getEmail())));
    }

    @Test
    @DirtiesContext
    void getSavedUserByIdApi() throws Exception {

        UserData userDataToSave = new UserData(null, "PreSavedUserName", "presaved_user_email@gmail.com");

        UserData userDataSaved = userStorageService.saveUser(userDataToSave);

        mockMvc.perform(get("/userdata/" + userDataSaved.getId()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(containsString(userDataToSave.getId().toString())))
                .andExpect(content().string(containsString(userDataToSave.getName())))
                .andExpect(content().string(containsString(userDataToSave.getEmail())));
    }

    @Test
    void getByIdExceptionThrown() throws Exception {

        long idNotPresent = 1001;

        mockMvc.perform(get("/userdata/" + idNotPresent))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void saveUserReturnsValidUser() {

        UserData userDataToSave = new UserData(null, "Alice", "alice@mail.ru");

        UserData userDataSaved = userStorageService.saveUser(userDataToSave);

        Assertions.assertEquals(userDataToSave.getEmail(),
                userDataSaved.getEmail());
        Assertions.assertEquals(userDataToSave.getName(),
                userDataSaved.getName());

        Assertions.assertNotNull(userDataSaved.getId());
        Assertions.assertTrue(userDataSaved.getId() > 0);
    }

    @Test
    void savedUserPersists() {

        UserData userDataToSave = new UserData(null, "PersistedUser", "persisted_user@mail.ru");

        UserData userDataSaved = userStorageService.saveUser(userDataToSave);

        AtomicReference<UserData> userDataRetrieved = new AtomicReference<>();

        Assertions.assertDoesNotThrow(
                () -> userDataRetrieved.set(userStorageService.getById(userDataSaved.getId()))
        );

        Assertions.assertEquals(userDataToSave.getName(),
                userDataRetrieved.get().getName());
        Assertions.assertEquals(userDataToSave.getEmail(),
                userDataRetrieved.get().getEmail());

        Assertions.assertNotNull(userDataRetrieved.get().getId());
    }
}
