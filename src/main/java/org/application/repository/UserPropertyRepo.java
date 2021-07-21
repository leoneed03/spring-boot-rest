package org.application.repository;

import org.application.model.property.UserProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPropertyRepo extends JpaRepository<UserProperty, Long> {

    @Query(value = "select * from TBL_PROP TP where TP.OWNER_USER_ID=:uid"
            , nativeQuery = true
    )
    List<UserProperty> getAllPropertyList(Long uid);

    @Query(value = "select * from TBL_PROP TP inner join TBL_USERS TU on TP.OWNER_USER_ID = TU.USER_ID where TU.USER_EMAIL = :email"
            , nativeQuery = true
    )
    List<UserProperty> getAllUserPropertyByEmailList(String email);
}
