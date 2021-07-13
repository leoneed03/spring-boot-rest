package org.application.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class UserException extends Exception {

    @Getter
    @Setter
    private HttpStatus status;

    public UserException(String message,
                         HttpStatus status) {
        super(message);
        this.status = status;
    }
}
