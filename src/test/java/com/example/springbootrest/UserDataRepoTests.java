package com.example.springbootrest;

import org.application.SpringBootRestApplication;
import org.application.exceptions.UserException;
import org.application.model.user.UserData;
import org.application.repository.UserDataRepo;
import org.application.service.UserStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest(classes = {SpringBootRestApplication.class})
public class UserDataRepoTests {
    @Autowired
    UserStorageService userStorageService;

    @MockBean
    UserDataRepo userDataRepo;

    private UserData getSomeUser(Long id) {
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
                Mockito.times(1)).save(userToSave);
    }

    @Test
    public void testGetByIdUserExceptionThrown() {
        long idSaved = 10;
        long idNotPresent = 0;

        UserData userToReturn = getSomeUser(idSaved);

        Mockito.doReturn(Optional.of(userToReturn))
                .when(userDataRepo)
                .findById(idSaved);

        Assertions.assertThrows(UserException.class,
                () -> userStorageService.getById(idNotPresent));
    }

    @Test
    public void testUpdateUserExceptionNotThrown() {
        long idSaved = 101;

        UserData userToReturn = getSomeUser(idSaved);

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
        long idNotFound = 103;

        UserData userToReturn = getSomeUser(idSaved);

        Mockito.doReturn(Optional.of(userToReturn))
                .when(userDataRepo)
                .findById(idSaved);

        Assertions.assertThrows(UserException.class,
                () -> userStorageService.updateIfPresent(
                        idNotFound,
                        getOtherUser(null)));
    }
}
