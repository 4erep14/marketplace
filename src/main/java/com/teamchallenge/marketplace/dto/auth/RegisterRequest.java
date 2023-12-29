package com.teamchallenge.marketplace.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Size(min = 2, max = 50)
    @Pattern(regexp = "[A-Z][a-z]+(-?[A-Z][a-z]+)?", message = "Name should starts with capital letter")
    @JsonProperty("first_name")
    private String firstName;

    @Size(min = 2, max = 50)
    @Pattern(regexp = "[A-Z][a-z]+(-?[A-Z][a-z]+)?", message = "Last name should starts with capital letter")
    @JsonProperty("last_name")
    private String lastName;


    @Size(min = 3, max = 64)
    @Pattern(regexp = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", message = "invalid email address")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",  message = "Password should contain at least one capital letter, one digit and one special symbol. Its length should be between 8 and 16")
    private String password;

    private String phone;

}
