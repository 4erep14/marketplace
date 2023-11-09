package com.teamchallenge.marketplace.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class NullEntityReferenceException extends RuntimeException {
    public NullEntityReferenceException(String message) {
        super(message);
    }
}

