package com.teamchallenge.marketplace.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserResponseDto(
        long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        LocalDate birthDate,
        List<Long> favourites,
        List<Long> cart
) {
}
