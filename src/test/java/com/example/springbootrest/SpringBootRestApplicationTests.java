package com.example.springbootrest;

import org.application.SpringBootRestApplication;
import org.application.exceptions.UserException;
import org.application.model.user.UserData;
import org.application.service.UserStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest(classes = {SpringBootRestApplication.class})
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class SpringBootRestApplicationTests {

    @Autowired
    private UserStorageService userStorageService;

    @Test
    void saveUserReturnsValidUser() {

        UserData userDataToSave = new UserData(null, "Alice", "alice@mail.ru");
        UserData userDataSaved = userStorageService.saveUser(userDataToSave);

        Assertions.assertEquals(userDataToSave.getEmail(), userDataSaved.getEmail());
        Assertions.assertEquals(userDataToSave.getName(), userDataSaved.getName());

        Assertions.assertNotNull(userDataSaved.getId());
        Assertions.assertTrue(userDataSaved.getId() > 0);
    }

    @Test
    void savedUserPersists() {

        UserData userDataToSave = new UserData(null, "PersistedUser", "persisted_user@mail.ru");
        UserData userDataSaved = userStorageService.saveUser(userDataToSave);

        UserData userDataRetrieved = null;

        try {
            userDataRetrieved = userStorageService.getById(userDataSaved.getId());
        } catch (UserException userException) {
            Assertions.fail();
        }

        System.out.println(userDataRetrieved);

        Assertions.assertEquals(userDataToSave.getName(), userDataRetrieved.getName());
        Assertions.assertEquals(userDataToSave.getEmail(), userDataRetrieved.getEmail());

        Assertions.assertNotNull(userDataRetrieved.getId());
    }
}
