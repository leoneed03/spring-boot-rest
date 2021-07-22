package org.application.service;

import org.application.aspects.LogThisExecutionTime;
import org.application.exceptions.UserException;
import org.application.model.user.UserData;
import org.application.repository.UserDataRepo;
import org.application.repository.UserPropertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserStorageService {
    private final UserDataRepo repo;
    private final UserServiceMessageHelper userServiceMessageHelper;

    @Autowired
    public UserStorageService(UserDataRepo repo,
                              UserServiceMessageHelper userServiceMessageHelper,
                              UserPropertyRepo propertyRepo) {
        this.repo = repo;
        this.userServiceMessageHelper = userServiceMessageHelper;
    }

    @Transactional
    public UserData saveUser(final UserData user) {

        return repo.save(user);
    }

    public List<UserData> getAllUsers() {

        return repo.findAll();
    }

    @Transactional
    public void deleteById(final Long id) {

        repo.deleteById(id);
    }

    @LogThisExecutionTime
    public UserData getById(final Long id) throws UserException {

        return repo.findById(id).orElseThrow(() ->
                new UserException(userServiceMessageHelper.getUserNotFound(id),
                        HttpStatus.NOT_FOUND));
    }

    @Transactional
    public UserData updateIfPresent(final Long id,
                                    final UserData user) throws UserException {
        Optional<UserData> userDataFoundOpt = repo.findById(id);

        UserData userDataFound = userDataFoundOpt.orElseThrow(() ->
                new UserException(userServiceMessageHelper.getUserNotFound(id),
                        HttpStatus.NOT_FOUND));

        userDataFound.setName(user.getName());
        userDataFound.setEmail(user.getEmail());

        return userDataFound;
    }
}
