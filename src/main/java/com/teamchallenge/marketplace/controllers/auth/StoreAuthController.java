package com.teamchallenge.marketplace.controllers.auth;

import com.teamchallenge.marketplace.dto.auth.AuthenticationRequest;
import com.teamchallenge.marketplace.dto.auth.AuthenticationResponse;
import com.teamchallenge.marketplace.dto.auth.StoreRegisterRequest;
import com.teamchallenge.marketplace.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/store")
@RequiredArgsConstructor
public class StoreAuthController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerSeller(
            @RequestBody StoreRegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerStore(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateSeller(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticateStore(request));
    }
}
