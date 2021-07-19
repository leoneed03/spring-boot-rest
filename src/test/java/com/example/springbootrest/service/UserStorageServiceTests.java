package com.example.springbootrest.service;

import org.application.SpringBootRestApplication;
import org.application.exceptions.UserException;
import org.application.model.user.UserData;
import org.application.service.UserServiceMessageHelper;
import org.application.service.UserStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest(classes = {SpringBootRestApplication.class})
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserStorageServiceTests {

    @Autowired
    private UserStorageService userStorageService;

    @Autowired
    private UserServiceMessageHelper userServiceMessageHelper;

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
        preSavedUser = userStorageService.saveUser(new UserData(null, "PreSavedUserName", "presaved_user_email@gmail.com"));
    }

    @Test
    public void testUpdateUserExceptionThrown() {
        long idNotPresent = -103;

        UserData userDataToUpdate = new UserData(null, "SomeUserName", "some_email@gmail.com");

        UserException userNotFound = Assertions.assertThrows(
                UserException.class,
                () -> userStorageService.updateIfPresent(
                        idNotPresent,
                        userDataToUpdate));

        Assertions.assertEquals(userNotFound.getMessage(),
                userServiceMessageHelper.getUserNotFound(idNotPresent));
        Assertions.assertEquals(userNotFound.getStatus(),
                HttpStatus.NOT_FOUND);
    }

    @Test
    public void testUpdateUser() {
        long idPresent = getPreSavedUser().getId();

        UserData userDataToUpdate = new UserData(null, "SomeUserName", "some_email@gmail.com");

        Assertions.assertDoesNotThrow(
                () -> userStorageService.updateIfPresent(
                        idPresent,
                        userDataToUpdate));
        AtomicReference<UserData> userDataFound = new AtomicReference<>();

        Assertions.assertDoesNotThrow(() -> userDataFound.set(userStorageService.getById(idPresent)));

        Assertions.assertEquals(userDataToUpdate.getEmail(), userDataFound.get().getEmail());
        Assertions.assertEquals(userDataToUpdate.getName(), userDataFound.get().getName());
    }
}
