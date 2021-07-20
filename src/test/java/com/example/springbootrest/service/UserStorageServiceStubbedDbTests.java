package com.example.springbootrest.service;

import org.application.SpringBootRestApplication;
import org.application.repository.UserDataRepo;
import org.application.service.UserServiceMessageHelper;
import org.application.service.UserStorageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = {SpringBootRestApplication.class})
public class UserStorageServiceStubbedDbTests {
    @Autowired
    UserStorageService userStorageService;

    @MockBean
    UserDataRepo userDataRepo;

    @Test
    void testGetStubbedRepo() {
    }
}
