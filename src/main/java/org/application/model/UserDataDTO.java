package org.application.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDataDTO {
    private Long id;
    private String name;
    private String email;
}
