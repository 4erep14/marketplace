package com.teamchallenge.marketplace.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerRegisterRequest {
    private String companyName;
    private String email;
    private String password;
}