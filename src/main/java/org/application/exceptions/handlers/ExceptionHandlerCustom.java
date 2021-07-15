package org.application.exceptions.handlers;

import org.application.exceptions.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public UserException handleException(MethodArgumentNotValidException exception) {

        return new UserException(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
