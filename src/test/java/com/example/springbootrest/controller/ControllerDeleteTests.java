package com.example.springbootrest.controller;

import org.application.SpringBootRestApplication;
import org.application.model.user.UserData;
import org.application.service.UserStorageService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(classes = {SpringBootRestApplication.class})
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ControllerDeleteTests {

    @Autowired
    private UserStorageService userStorageService;

    @Autowired
    private MockMvc mockMvc;

    private static final PathControllerHelper pathControllerHelper = new PathControllerHelper();

    private UserData getPreSavedUser() {
        return preSavedUser;
    }

    private UserData preSavedUser = null;

    @BeforeEach
    void addUsers() {
        preSavedUser = userStorageService.saveUser(new UserData(null, "PreSavedUserName", "presaved_user_email@gmail.com"));
    }

    @Test
    void userNotGetsDeletedUserExceptionNotThrown() throws Exception {

        long idNotPresent = -1000;
        userStorageService.saveUser(new UserData(null, "Test1", "test1@gmail.com"));


        mockMvc.perform(delete(pathControllerHelper.getRootPath() + idNotPresent))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(get(pathControllerHelper.getRootPath()))
                .andDo(print());
    }

    @Test
    void userGetsDeleted() throws Exception {
        userStorageService.saveUser(new UserData(null, "Test2", "test2@gmail.com"));

        mockMvc.perform(get(pathControllerHelper.getRootPath()))
                .andExpect(content().string(containsString(
                        getPreSavedUser().getEmail())));

        mockMvc.perform(get(pathControllerHelper.getRootPath()))
                .andDo(print());
        mockMvc.perform(delete(pathControllerHelper.getRootPath() + preSavedUser.getId()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(get(pathControllerHelper.getRootPath()))
                .andExpect(content().string(allOf(Matchers.not(containsString(
                        getPreSavedUser().getEmail())))));
    }
    @Test
    void userGetsDeleted2() throws Exception {
        userStorageService.saveUser(new UserData(null, "Test3", "test3@gmail.com"));

        mockMvc.perform(get(pathControllerHelper.getRootPath()))
                .andExpect(content().string(containsString(
                        getPreSavedUser().getEmail())));

        mockMvc.perform(get(pathControllerHelper.getRootPath()))
                .andDo(print());

        mockMvc.perform(delete(pathControllerHelper.getRootPath() + preSavedUser.getId()))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(get(pathControllerHelper.getRootPath()))
                .andExpect(content().string(allOf(Matchers.not(containsString(
                        getPreSavedUser().getEmail())))));
    }

}
