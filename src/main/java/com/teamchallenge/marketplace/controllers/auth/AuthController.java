package com.teamchallenge.marketplace.controllers.auth;

import com.teamchallenge.marketplace.dto.auth.AuthenticationRequest;
import com.teamchallenge.marketplace.dto.auth.AuthenticationResponse;
import com.teamchallenge.marketplace.dto.auth.CustomerRegisterRequest;
import com.teamchallenge.marketplace.dto.auth.SellerRegisterRequest;
import com.teamchallenge.marketplace.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/customer/register")
    public ResponseEntity<AuthenticationResponse> registerCustomer(
            @RequestBody CustomerRegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerCustomer(request));
    }

    @PostMapping("/seller/register")
    public ResponseEntity<AuthenticationResponse> registerSeller(
            @RequestBody SellerRegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerSeller(request));
    }

    @PostMapping("/customer/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateCustomer(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticateCustomer(request));
    }

    @PostMapping("/seller/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateSeller(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticateSeller(request));
    }

}
