package com.teamchallenge.marketplace.handler;

import com.teamchallenge.marketplace.exception.NullEntityReferenceException;
import com.teamchallenge.marketplace.exception.UserNotValidException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIllegalStateException(IllegalStateException exception){
        logger.error(exception.getMessage());

        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }
    @ExceptionHandler(UserNotValidException.class)
    public ResponseEntity<?> handleUserNotValidException(UserNotValidException exception){
        logger.error(exception.getErrorMessages().toString());

        return ResponseEntity
                .badRequest()
                .body(exception.getErrorMessages());
    }

    @ExceptionHandler(NullEntityReferenceException.class)
    public ResponseEntity<?> handleNullEntityReferenceException(NullEntityReferenceException exception) {
        logger.error(exception.getMessage());

        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleNullEntityNotFoundException(EntityNotFoundException exception) {
        logger.error(exception.getMessage());

        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniesException(AccessDeniedException exception) {
        logger.error(exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleInternalServerError(Exception exception) {
        logger.error("{}, {}", exception.getClass().getName(), exception.getMessage());

        System.out.println(Arrays.toString(exception.getStackTrace()));
        return ResponseEntity
                .internalServerError()
                .body(exception.getMessage());
    }
}
