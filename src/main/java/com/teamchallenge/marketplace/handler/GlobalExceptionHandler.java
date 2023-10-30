package com.teamchallenge.marketplace.handler;

import com.teamchallenge.marketplace.exception.UserNotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleException(IllegalStateException exception){
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }
    @ExceptionHandler(UserNotValidException.class)
    public ResponseEntity<?> handleException(UserNotValidException exception){
        return ResponseEntity
                .badRequest()
                .body(exception.getErrorMessages());
    }
}
