package org.application.service;

import org.application.model.UserData;
import org.application.repository.UserDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserStorageService {
    private final UserDataRepo repo;

    @Autowired
    public UserStorageService(UserDataRepo repo) {
        this.repo = repo;
    }

    @Transactional
    public UserData saveUser(final UserData user) {

        return repo.save(user);
    }

    @Transactional
    public List<UserData> getAllUsers() {

        return repo.findAll();
    }

    @Transactional
    public boolean deleteById(final Long id) {

        Integer userDeleted = repo.deleteUserDataById(id);

        return userDeleted != 0;
    }

    @Transactional
    public Optional<UserData> getById(final Long id) {

        return repo.findById(id);
    }

    @Transactional
    public Optional<UserData> updateIfPresent(final Long id,
                                              final UserData user) {
        Optional<UserData> userDataFoundOpt = repo.findById(id);

        if (userDataFoundOpt.isEmpty()) {
            return Optional.empty();
        }

        UserData userDataFound = userDataFoundOpt.get();

        userDataFound.setName(user.getName());
        userDataFound.setEmail(user.getEmail());

        return Optional.of(userDataFound);
    }
}
