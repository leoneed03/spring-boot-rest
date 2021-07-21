package com.example.springbootrest.service;

import org.application.SpringBootRestApplication;
import org.application.exceptions.UserException;
import org.application.model.property.UserProperty;
import org.application.model.property.UserPropertyDTO;
import org.application.model.user.UserData;
import org.application.repository.UserDataRepo;
import org.application.repository.UserPropertyRepo;
import org.application.service.UserServiceMessageHelper;
import org.application.service.UserStorageService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest(classes = {SpringBootRestApplication.class})
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class UserStorageServiceTests {

    @Autowired
    private UserStorageService userStorageService;

    @Autowired
    private UserDataRepo userDataRepo;

    @Autowired
    private UserPropertyRepo userPropertyRepo;

    @Autowired
    private UserServiceMessageHelper userServiceMessageHelper;

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

    @Test
    public void testGetProperty() {
        System.out.println(userStorageService.getUserProperty());
    }

    @Test
    public void testGetAllPropertyById() {
        System.out.println(userPropertyRepo.getAllPropertyList((long) 3));
    }

    @Test
    public void testGetAllPropertyByEmail() {
        System.out.println(userPropertyRepo.getAllUserPropertyByEmailList("user_3@mail.ru"));
    }
}
