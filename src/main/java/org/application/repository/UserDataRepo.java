package org.application.repository;

import org.application.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepo extends JpaRepository<UserData, Long> {

    Integer deleteUserDataById(Long id);
}
