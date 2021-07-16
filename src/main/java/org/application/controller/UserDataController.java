package org.application.controller;

import org.application.exceptions.UserException;
import org.application.model.UserData;
import org.application.model.UserDataDTO;
import org.application.model.UserDataValidator;
import org.application.model.mapping.UserMapper;
import org.application.service.UserServiceMessageHelper;
import org.application.service.UserStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userdata")
public class UserDataController {
    private final UserStorageService userStorageService;
    private final UserDataValidator userDataValidator;
    private final UserServiceMessageHelper userServiceMessageHelper;
    private final UserMapper userMapper;

    @Autowired
    public UserDataController(UserStorageService userStorageService,
                              UserDataValidator userDataValidator,
                              UserServiceMessageHelper userServiceMessageHelper,
                              UserMapper userMapper) {
        this.userStorageService = userStorageService;
        this.userDataValidator = userDataValidator;
        this.userServiceMessageHelper = userServiceMessageHelper;
        this.userMapper = userMapper;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDataDTO saveUser(@RequestBody UserDataDTO userDataDTO) throws UserException {

        UserData user = userMapper.toUserData(userDataDTO);

        if (user == null) {

            throw new UserException(
                    userServiceMessageHelper.getNullUserMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        if (!userDataValidator.isUserValidNoId(user)) {

            throw new UserException(userServiceMessageHelper.getInvalidUserParameters(),
                    HttpStatus.BAD_REQUEST);
        }

        try {

            return userMapper.toUserDataDTO(userStorageService.saveUser(user));

        } catch (DataIntegrityViolationException integrityViolationException) {

            throw new UserException(integrityViolationException.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public UserData updateUser(@PathVariable("id") Long userId,
                               @RequestBody UserData user) throws UserException {

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

        return userStorageService.updateIfPresent(userId, user);
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

        try {

            userStorageService.deleteById(userId);

        } catch (EmptyResultDataAccessException ignored) {
        }

    }

    @GetMapping("/{id}")
    public UserData getUser(@PathVariable("id") Long userId) throws UserException {

        if (userId == null) {

            throw new UserException(userServiceMessageHelper.getNullIdMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        return userStorageService.getById(userId);
    }
}
