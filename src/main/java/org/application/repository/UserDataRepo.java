package org.application.repository;

import org.application.model.user.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepo extends JpaRepository<UserData, Long> {
    Optional<UserData> findUserDataByEmail(String email);
    Optional<UserData> findTopByName(String email);
}
