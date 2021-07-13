package org.application.service;

import org.springframework.stereotype.Component;

@Component
public class UserServiceMessageHelper {

    public String getNullIdMessage() {
        return "provided id is null";
    }

    public String getNullUserMessage() {
        return "provided user is null";
    }

    public String getViolationMessage() {
        return "provided user violates constraints";
    }

    public String getUserNotFound(Long userId) {
        return "user not found by id: " + userId;
    }

    public String getInvalidUserParameters() {
        return "provided user is invalid";
    }
}
