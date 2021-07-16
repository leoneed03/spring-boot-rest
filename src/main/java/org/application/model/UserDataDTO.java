package org.application.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;

public class UserDataDTO {
    private String name;
    private String email;
}
