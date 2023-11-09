package com.teamchallenge.marketplace.controllers.auth;

import com.teamchallenge.marketplace.dto.auth.AuthenticationRequest;
import com.teamchallenge.marketplace.dto.auth.AuthenticationResponse;
import com.teamchallenge.marketplace.dto.auth.StoreRegisterRequest;
import com.teamchallenge.marketplace.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logInfo = LoggerFactory.getLogger(StoreAuthController.class);

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerSeller(
            @RequestBody StoreRegisterRequest request
    ) {
        logInfo.info("Seller registration request:{}", request.getEmail());
        AuthenticationResponse response =service.registerStore(request);
        logInfo.info("Seller registered:{}", response.token());
        return ResponseEntity.ok(service.registerStore(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateSeller(
            @RequestBody AuthenticationRequest request
    ) {
        logInfo.info("Seller authenticate request:{}", request.getEmail());
        AuthenticationResponse response =service.authenticateStore(request);
        logInfo.info("Seller registered:{}", response.token());
        return ResponseEntity.ok(service.authenticateStore(request));
    }
}
