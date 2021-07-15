package org.application.controller;

import org.application.exceptions.UserException;
import org.application.model.UserData;
import org.application.model.UserDataValidator;
import org.application.service.UserServiceMessageHelper;
import org.application.service.UserStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/userdata")
public class UserDataController {
    private final UserStorageService userStorageService;
    private final UserDataValidator userDataValidator;
    private final UserServiceMessageHelper userServiceMessageHelper;

    @Autowired
    public UserDataController(UserStorageService userStorageService,
                              UserDataValidator userDataValidator,
                              UserServiceMessageHelper userServiceMessageHelper) {
        this.userStorageService = userStorageService;
        this.userDataValidator = userDataValidator;
        this.userServiceMessageHelper = userServiceMessageHelper;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(UserData.class)
    public UserData saveUser(@Valid @RequestBody UserData user) throws UserException {

        if (user == null) {

            throw new UserException(
                    userServiceMessageHelper.getNullUserMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        if (!userDataValidator.isUserValidNoId(user)) {

            throw new UserException(userServiceMessageHelper.getInvalidUserParameters(),
                    HttpStatus.BAD_REQUEST);
        }

        return userStorageService.saveUser(user);
    }

    @PutMapping("/{id}")
    public UserData updateUser(@PathVariable("id") Long userId,
                               @RequestBody @Valid UserData user) throws UserException {

        if (userId == null) {

            throw new UserException(
                    userServiceMessageHelper.getNullIdMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        if (user == null) {

            throw new UserException(
                    userServiceMessageHelper.getNullUserMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        if (!userDataValidator.isUserValidNoId(user)) {

            throw new UserException(
                    userServiceMessageHelper.getInvalidUserParameters(),
                    HttpStatus.BAD_REQUEST);
        }

        return userStorageService.updateIfPresent(userId, user).orElseThrow(
                () -> new UserException(userServiceMessageHelper.getUserNotFound(userId),
                        HttpStatus.NOT_FOUND)
        );
    }

    @GetMapping("/")
    public List<UserData> getAllUsers() {
        return userStorageService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long userId) throws UserException {

        if (userId == null) {

            throw new UserException(userServiceMessageHelper.getNullIdMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        boolean userWasFound = userStorageService.deleteById(userId);

        if (!userWasFound) {

            throw new UserException(userServiceMessageHelper.getUserNotFound(userId),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public UserData getUser(@PathVariable("id") Long userId) throws UserException {

        if (userId == null) {

            throw new UserException(userServiceMessageHelper.getNullIdMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        Optional<UserData> userDataFound = userStorageService.getById(userId);

        return userDataFound.orElseThrow(
                () -> new UserException(userServiceMessageHelper.getUserNotFound(userId),
                        HttpStatus.NOT_FOUND)
        );
    }
}
