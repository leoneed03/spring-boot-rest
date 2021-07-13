package org.application.exceptions.handlers;

import org.application.exceptions.UserException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ExceptionHandlerCustom {

    @ExceptionHandler(UserException.class)
    public UserException handleUserException(HttpServletResponse httpResponse,
                                             UserException userException) {
        httpResponse.setStatus(userException.getStatus().value());

        return userException;
    }
}
