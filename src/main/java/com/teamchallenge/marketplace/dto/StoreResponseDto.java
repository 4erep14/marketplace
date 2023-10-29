package com.teamchallenge.marketplace.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record StoreResponseDto(
        long id,
        String companyName,
        List<Long> products,
        long ownerId
) {
}
