package com.teamchallenge.marketplace.controllers.auth;

import com.teamchallenge.marketplace.dto.auth.AuthenticationRequest;
import com.teamchallenge.marketplace.dto.auth.AuthenticationResponse;
import com.teamchallenge.marketplace.dto.auth.RegisterRequest;
import com.teamchallenge.marketplace.dto.auth.StoreRegisterRequest;
import com.teamchallenge.marketplace.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerCustomer(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerUser(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateCustomer(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticateCustomer(request));
    }
    @PostMapping("/validation")
    public  ResponseEntity<String> postUserValidation(
            @RequestBody RegisterRequest registerRequest, StoreRegisterRequest storeRegisterRequest ){
        final String userValidation= String.valueOf(service.registerUser(registerRequest));
        return ResponseEntity
                .accepted()
                .body(userValidation);
    }
    @GetMapping("/error")
    public ResponseEntity<?> throwException(){
        return ResponseEntity.ok(service.throwException());
    }


}
