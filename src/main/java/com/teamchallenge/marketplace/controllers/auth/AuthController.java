package com.teamchallenge.marketplace.controllers.auth;

import com.teamchallenge.marketplace.dto.auth.AuthenticationRequest;
import com.teamchallenge.marketplace.dto.auth.AuthenticationResponse;
import com.teamchallenge.marketplace.dto.auth.RegisterRequest;
import com.teamchallenge.marketplace.dto.auth.StoreRegisterRequest;
import com.teamchallenge.marketplace.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;
    private static final Logger logInfo = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody RegisterRequest request
    ) {
        logInfo.info("User registration request:{}", request.getEmail());
        AuthenticationResponse response=service.registerUser(request);
        logInfo.info("User registered:{}", response.token());
        return ResponseEntity.ok(service.registerUser(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateUser(
            @RequestBody AuthenticationRequest request
    ) {
        logInfo.info("User authentication request:{}", request.getEmail());
        AuthenticationResponse response=service.authenticateCustomer(request);
        logInfo.info("User authentication:{}", response.token());
        return ResponseEntity.ok(service.authenticateCustomer(request));
    }
    @PostMapping("/validation")
    public  ResponseEntity<String> postUserValidation(
            @RequestBody RegisterRequest registerRequest, StoreRegisterRequest storeRegisterRequest ){
        final String userValidation= String.valueOf(service.registerUser(registerRequest));
        logInfo.info("User validation request:{} ", registerRequest.getEmail());
        String userValid = String.valueOf(service.registerUser(registerRequest));
        logInfo.info("Validation result: {}", userValid);
        return ResponseEntity
                .accepted()
                .body(userValidation);

    }
    @GetMapping("/error")
    public ResponseEntity<?> throwException(){
        try {
            service.throwException() ;

        }catch (Exception e){
            logInfo.error("...", e.getMessage());
        }

        return ResponseEntity.ok(service.throwException());
    }


}
