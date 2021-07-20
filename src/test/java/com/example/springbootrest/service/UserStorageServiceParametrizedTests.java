package com.example.springbootrest.service;

import org.application.SpringBootRestApplication;
import org.application.model.user.UserData;
import org.application.repository.UserDataRepo;
import org.application.service.UserStorageService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = {SpringBootRestApplication.class})
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserStorageServiceParametrizedTests {

    @Autowired
    private UserStorageService userStorageService;

    @Autowired
    private UserDataRepo userDataRepo;

    List<UserData> userDataList = new ArrayList<>();

    @BeforeAll
    void saveUsers() {
        userDataList.add(userDataRepo.save(new UserData(null, "First", "1@gmail.com")));
        userDataList.add(userDataRepo.save(new UserData(null, "Second", "2@gmail.com")));
        userDataList.add(userDataRepo.save(new UserData(null, "Third", "3@gmail.com")));
        userDataList.add(userDataRepo.save(new UserData(null, "Third", "3_broken@gmail.com")));
    }

    @AfterAll
    void deleteUsers() {

        userDataList.forEach(x -> {
            if (userDataRepo.findById(x.getId()).isPresent()) {
                userDataRepo.deleteById(x.getId());
            }
        });
    }

    @Test
    void testParametrizedUsersGet1() {
        System.out.println("#1 test");
        userStorageService.getAllUsers().forEach(System.out::println);
        System.out.println("#1 test end");
    }

    @Test
    void testParametrizedUsersGet2() {
        System.out.println("#2 start");
        userStorageService.getAllUsers().forEach(System.out::println);
        System.out.println("#2 end");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1@gmail.com", "2@gmail.com", "3@gmail.com"})
    void checkIfAllEmailsArePresent(String email) {
        System.out.println("Current email: " + email);
        Optional<UserData> userData = userDataRepo.findUserDataByEmail(email);

        System.out.println(userData.orElse(null));

    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second", "Third"})
    void checkIfAllNamesArePresent(String name) {
        System.out.println("Current name: " + name);
        Optional<UserData> userData = userDataRepo.findTopByName(name);

        System.out.println(userData.orElse(null));

    }
}
