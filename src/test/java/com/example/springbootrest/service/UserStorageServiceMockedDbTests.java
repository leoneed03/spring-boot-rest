package com.example.springbootrest.service;

import org.application.SpringBootRestApplication;
import org.application.exceptions.UserException;
import org.application.model.user.UserData;
import org.application.repository.UserDataRepo;
import org.application.service.UserServiceMessageHelper;
import org.application.service.UserStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@SpringBootTest(classes = {SpringBootRestApplication.class})
public class UserStorageServiceMockedDbTests {
    @Autowired
    UserStorageService userStorageService;

    @Autowired
    UserServiceMessageHelper userServiceMessageHelper;

    @MockBean
    UserDataRepo userDataRepo;

    private UserData createSomeUser(Long id) {
        return new UserData(id, "SomeUserName", "some_email@gmail.com");
    }

    private UserData getOtherUser(Long id) {
        return new UserData(id, "OtherUserName", "other_user_email@gmail.com");
    }

    @Test
    public void testUserRepoSaveInvocation() {

        UserData userToSave = new UserData();

        userToSave.setEmail("user@mail.ru");
        userToSave.setName("Andrew");
        userStorageService.saveUser(userToSave);

        Mockito.verify(userDataRepo,
                Mockito.times(1))
                .save(userToSave);
    }

    @Test
    public void testGetByIdUserExceptionThrown() {
        long idSaved = 10;
        long idNotPresent = 0;

        UserData userToReturn = createSomeUser(idSaved);

        Mockito.doReturn(Optional.of(userToReturn))
                .when(userDataRepo)
                .findById(idSaved);

        UserException userNotFound = Assertions.assertThrows(
                UserException.class,
                () -> userStorageService.getById(idNotPresent));

        Assertions.assertEquals(userNotFound.getStatus(),
                HttpStatus.NOT_FOUND);
        Assertions.assertEquals(userNotFound.getMessage(),
                userServiceMessageHelper.getUserNotFound(idNotPresent));
    }

    @Test
    public void testUpdateUserExceptionNotThrown() {
        long idSaved = 101;

        UserData userToReturn = createSomeUser(idSaved);

        Mockito.doReturn(Optional.of(userToReturn))
                .when(userDataRepo)
                .findById(idSaved);

        Assertions.assertDoesNotThrow(
                () -> userStorageService.updateIfPresent(
                        idSaved,
                        getOtherUser(null)));
    }

    @Test
    public void testUpdateUserExceptionThrown() {
        long idSaved = 102;
        long idNotPresent = 103;

        UserData userToReturn = createSomeUser(idSaved);

        Mockito.doReturn(Optional.of(userToReturn))
                .when(userDataRepo)
                .findById(idSaved);

        UserException userNotFound = Assertions.assertThrows(
                UserException.class,
                () -> userStorageService.updateIfPresent(
                        idNotPresent,
                        getOtherUser(null)));

        Assertions.assertEquals(userNotFound.getMessage(),
                userServiceMessageHelper.getUserNotFound(idNotPresent));
        Assertions.assertEquals(userNotFound.getStatus(),
                HttpStatus.NOT_FOUND);
    }
}
