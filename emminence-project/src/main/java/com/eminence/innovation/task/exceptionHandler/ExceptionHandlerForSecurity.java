package com.eminence.innovation.task.exceptionHandler;

import ch.qos.logback.core.testUtil.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerForSecurity extends RuntimeException {

    @Autowired
    public ErrorResponse errorResponse;

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<String> userAlreadyRegistered(Throwable throwable) {
        errorResponse.setErrorMessage(throwable.getLocalizedMessage());
        errorResponse.setStatus(HttpStatus.IM_USED);
        errorResponse.setErrorDetails(throwable.getCause().getMessage());
        return new ResponseEntity(errorResponse, HttpStatus.IM_USED);
    }
}
