package com.example.springbootrest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.application.SpringBootRestApplication;
import org.application.model.mapping.UserMapper;
import org.application.model.user.UserData;
import org.application.model.user.UserDataDTO;
import org.application.repository.UserDataRepo;
import org.application.service.UserStorageService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(classes = {SpringBootRestApplication.class})
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class ControllerTests {

    @Autowired
    private UserStorageService userStorageService;

    @Autowired
    private UserDataRepo userDataRepo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserMapper userMapper;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final PathControllerHelper pathControllerHelper = new PathControllerHelper();

    private boolean listOfUsersContainsEmail(String emailToFind) {
        return userStorageService.getAllUsers().stream()
                .map(UserData::getEmail)
                .anyMatch(email -> email.equals(emailToFind));
    }

    private long getIdNotPresent() {
        return -1001;
    }

    private UserData getPreSavedUser() {
        return preSavedUser;
    }

    private UserData preSavedUser = null;

    @BeforeEach
    void addUsers() {

        preSavedUser = userDataRepo.save(new UserData(null, "PreSavedUserName", "presaved_user_email@gmail.com"));
    }

    @AfterEach
    void deleteUsers() {

        if (userDataRepo.findById(preSavedUser.getId()).isPresent()) {
            userDataRepo.deleteById(preSavedUser.getId());
        }
    }

    @Test
    void userNotGetsDeletedUserExceptionNotThrown() throws Exception {

        long idNotPresent = getIdNotPresent();
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

        System.out.println("GET ALL");
        mockMvc.perform(get(pathControllerHelper.getRootPath()))
                .andDo(print())
                .andExpect(content().string(allOf(Matchers.not(containsString(
                        getPreSavedUser().getEmail())))));

        Assertions.assertFalse(listOfUsersContainsEmail(getPreSavedUser().getEmail()));
        System.out.println("ASSERTIONS DONE");
    }

    @Test
    void userGetsUpdated() throws Exception {
        UserData userDataUpdated = new UserData(null, "UpdatedUserName", "updated_email@gmail.com");
        UserDataDTO userDataUpdatedDTO = userMapper.toUserDataDTO(userDataUpdated);

        mockMvc.perform(get(pathControllerHelper.getRootPath()))
                .andDo(print());

        String userAsString = objectMapper.writeValueAsString(userDataUpdatedDTO);

        mockMvc.perform(put(pathControllerHelper.getRootPath() + preSavedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userAsString))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(get(pathControllerHelper.getRootPath()))
                .andExpect(content().string(containsString(
                        userDataUpdated.getEmail())));

        Assertions.assertTrue(listOfUsersContainsEmail(userDataUpdated.getEmail()));
    }

    @Test
    void whenUpdatedUserNotFoundExceptionThrown() throws Exception {
        UserData userDataUpdated = new UserData(null, "UpdatedUserName", "updated_email@gmail.com");
        UserDataDTO userDataUpdatedDTO = userMapper.toUserDataDTO(userDataUpdated);

        mockMvc.perform(get(pathControllerHelper.getRootPath()))
                .andDo(print());

        String userAsString = objectMapper.writeValueAsString(userDataUpdatedDTO);

        mockMvc.perform(put(pathControllerHelper.getRootPath() + getIdNotPresent())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userAsString))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        Assertions.assertFalse(listOfUsersContainsEmail(userDataUpdated.getEmail()));
    }

    @Test
    void userGetSaved() throws Exception {
        UserData userDataUpdated = new UserData(null, "SavedUserName", "saved_email@gmail.com");
        UserDataDTO userDataUpdatedDTO = userMapper.toUserDataDTO(userDataUpdated);

        mockMvc.perform(get(pathControllerHelper.getRootPath()))
                .andDo(print());

        String userAsString = objectMapper.writeValueAsString(userDataUpdatedDTO);

        mockMvc.perform(post(pathControllerHelper.getRootPath())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userAsString))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Assertions.assertTrue(listOfUsersContainsEmail(userDataUpdated.getEmail()));
    }
}
