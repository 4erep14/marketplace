package com.teamchallenge.marketplace.exception;


import lombok.Data;

import java.util.Set;
@Data

public class UserNotValidException extends RuntimeException{
    private final Set<String> errorMessages;
}
