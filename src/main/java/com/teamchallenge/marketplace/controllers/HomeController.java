package com.teamchallenge.marketplace.controllers;

//import com.teamchallenge.marketplace.model.Customer;
import com.teamchallenge.marketplace.dto.auth.ChangePasswordRequest;
import com.teamchallenge.marketplace.model.User;
import com.teamchallenge.marketplace.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HomeController {

    private final UserService service;

    @GetMapping("/")
    public String sayHello() {
        return "Hello, Home!";
    }

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ){
        service.changePassword(request, connectedUser);
        return ResponseEntity
                .ok()
                .build();
    }
}
