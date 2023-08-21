package com.iberianmotorsports.service.exception;

import jakarta.validation.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleConflict(
            Exception ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(bodyOfResponse);
    }

    @ExceptionHandler(ServiceException.class)
    protected ResponseEntity<Object> handleConflict(
            ServiceException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(bodyOfResponse);
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    protected ResponseEntity<Object> handleConflict(
            ConstraintViolationException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(bodyOfResponse);
    }

//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<Object> handleConflict(
//            Exception ex, WebRequest request) {
//        String bodyOfResponse = ex.getMessage();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(bodyOfResponse);
//    }

}
