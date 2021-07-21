package com.example.springbootrest.service;

import com.example.springbootrest.service.config.TestConfig;
import org.application.SpringBootRestApplication;
import org.application.dummy.RepoDummy;
import org.application.dummy.RepoDummyImpl;
import org.application.model.user.UserData;
import org.application.repository.UserDataRepo;
import org.application.service.UserStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
//        (classes = TestConfig.class)
        (classes = {SpringBootRestApplication.class})
@Import(TestConfig.class)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class UserStorageServiceStubbedDbTests {
    //TODO: this does not work as it should
    @TestConfiguration
    static class MyTestConfiguration {
        @Bean
        public ModelMapper modelMapper() {
            ModelMapper modelMapper = new ModelMapper();

            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

            return modelMapper;
        }

        @Bean
        RepoDummy repoDummy() {
            return new RepoDummyImpl();
        }
    }

    @Autowired
    UserStorageService userStorageService;

    //    @Qualifier("repoDummyStub")
    @Autowired
    RepoDummy objectBeingStubbed;

//    @MockBean
//    UserDataRepo userDataRepoMock;

    @Autowired
    UserDataRepo userDataRepoReal;

    @Test
    void testStub() {
        objectBeingStubbed.save("STUBBED ARG");

        System.out.println(objectBeingStubbed.getAll());

        //should be 0 because we use stub
        Assertions.assertEquals(objectBeingStubbed.getAll().size(), 0);
    }

//    @Test
//    void testGetStubbedRepo() {
//        String stubMethodArg = "IS IT MOCKED?";
//
//        String userDataSaved = objectBeingStubbed.save("SOMETHING");
//        objectBeingStubbed.save("SAVED 2");
//
//        System.out.println(userDataSaved);
//
//        System.out.println(objectBeingStubbed.getAll());
//
//        Mockito.doReturn("STUB RETURN VALUE")
//                .when(objectBeingStubbed)
//                .save(stubMethodArg);
//
//        System.out.println("=============use mocked arg===============");
//        System.out.println(objectBeingStubbed.save(stubMethodArg));
//        System.out.println(objectBeingStubbed.getAll());
//        System.out.println("=============use real arg===============");
//        System.out.println(objectBeingStubbed.save("SAVE 3"));
//
//        System.out.println(objectBeingStubbed.getAll());
//
//        Assertions.assertEquals(objectBeingStubbed.getAll().size(), 3);
//
//        System.out.println("+++++++++++++REPO MOCK+++++++++++++++");
//    }

    @Test
    void testSpyOnObject() {
        String spiedArg = "SPIED";

        RepoDummy repoDummy = new RepoDummyImpl();

        RepoDummy repoDummySpy = Mockito.spy(repoDummy);

        Mockito.doReturn("SPIED VALUE RETURNED")
                .when(repoDummySpy)
                .save(spiedArg);

        repoDummySpy.save("FIRST");
        repoDummySpy.save(spiedArg);
        repoDummySpy.save("SPIED");

        System.out.println(repoDummySpy.getAll());
    }

    @Test
    void testSpyOnRepo() {
        UserDataRepo userDataRepo = Mockito.mock(UserDataRepo.class);

        UserData userDataSaved = userDataRepo.save(new UserData(null, "NAME", "spy@gmail.com"));

        System.out.println(userDataSaved);

        System.out.println(userDataRepo.findAll());
    }

    @Test
    void testSpyOnRepoDummy() {
        RepoDummy repo = Mockito.spy(RepoDummyImpl.class);

        System.out.println(repo.save("SAVED 1"));

        System.out.println(repo.getAll());
    }

    @Test
    void testRepoReal() {
        UserDataRepo userDataRepo = userDataRepoReal;

        System.out.println(userDataRepo.save(new UserData(null, "SAVEDNAME", "saved_email@gmail.com")));

        Assertions.assertFalse(userDataRepo.findAll().isEmpty());
    }

    @Disabled
    @Test
    void testSpyOnRepoReal() {
        UserDataRepo userDataRepo = Mockito.spy(userDataRepoReal);

        System.out.println(userDataRepo.save(new UserData(null, "SAVEDNAME", "saved_email@gmail.com")));

        Assertions.assertFalse(userDataRepo.findAll().isEmpty());
    }
}
