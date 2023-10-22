package com.teamchallenge.marketplace.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
