package com.teamchallenge.marketplace.validation;

import com.teamchallenge.marketplace.exception.UserNotValidException;
import jakarta.validation.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserValidator<T> {
    private final ValidatorFactory factory= Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();
    public void validate(T userValidate){
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
