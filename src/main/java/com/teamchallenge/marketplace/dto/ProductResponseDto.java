package com.teamchallenge.marketplace.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ProductResponseDto(
        long id,
        String name,
        String description,
        int price,
        long categoryId,
        long store
) {
}
