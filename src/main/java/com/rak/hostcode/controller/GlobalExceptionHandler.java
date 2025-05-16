package com.rak.hostcode.controller;

import com.rak.hostcode.dto.ErrorMessage;
import com.rak.hostcode.exception.DuplicateFieldException;
import com.rak.hostcode.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException e) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(DuplicateFieldException.class)
    public ResponseEntity<ErrorMessage> handleDuplicateFieldException(DuplicateFieldException e) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
