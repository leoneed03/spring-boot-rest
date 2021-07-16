package org.application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonPropertyOrder({"id"})
public class UserDataDTO {
    private Long id;
    private String name;
    private String email;
}
