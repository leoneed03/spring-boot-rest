package org.application.model;

import org.springframework.stereotype.Component;

@Component
public class UserDataValidator {

    public boolean isUserValidNoId(UserData user) {
//        return user.getId() == null
//                && isUserValid(user);
        return true;
    }

    public boolean isUserValid(UserData user) {
//        return user.getName() != null
//                && user.getEmail() != null;
        return true;
    }
}
