package com.example.springbootrest.service;

import com.example.springbootrest.service.config.TestConfig1;
import org.application.config.AppConfig;
import org.application.dummy.RepoDummy;
import org.application.dummy.RepoDummyImpl;
import org.application.model.user.UserData;
import org.application.repository.UserDataRepo;
import org.application.service.UserStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@Import(TestConfig1.class)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class UserStorageServiceStubbedDbTests {
    //TODO: why this does not work as it should?
//    @TestConfiguration
//    static class MyTestStubConfiguration {
//        @Bean
//        RepoDummy repoDummyStub() {
//            return new RepoDummyStub();
//        }
//    }

    @Autowired
    UserStorageService userStorageService;

    @Autowired
    RepoDummy repoDummyBeingStubbed;

    @Autowired
    UserDataRepo userDataRepoReal;

    @Test
    void testStub() {
        repoDummyBeingStubbed.save("STUBBED ARG");

        System.out.println(repoDummyBeingStubbed.getAll());

        //should be 0 and not 1 because stub is used
        Assertions.assertEquals(0, repoDummyBeingStubbed.getAll().size());
    }

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
