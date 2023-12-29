package com.teamchallenge.marketplace.validation;

import com.teamchallenge.marketplace.exception.UserNotValidException;
import jakarta.validation.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserValidator<T> {
    private final ValidatorFactory factory= Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();
    private final Logger logger = LoggerFactory.getLogger(UserValidator.class);

    public void validate(T userValidate){
        logger.info("validate {} object data", userValidate.getClass().getName());
        Set<ConstraintViolation<T>> violations= validator.validate(userValidate);
        if (!violations.isEmpty()){
            var errorMessage=violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
            throw new UserNotValidException(errorMessage);
       }
    }
}
