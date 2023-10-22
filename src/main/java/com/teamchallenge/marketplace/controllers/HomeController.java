package com.teamchallenge.marketplace.controllers;

import com.teamchallenge.marketplace.model.Customer;
import com.teamchallenge.marketplace.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HomeController {

    private final CustomerService service;

    @GetMapping("/")
    public ResponseEntity<String> sayHello() {
        service.create(Customer.builder()
                        .firstName("Pisya")
                        .lastName("Popa")
                        .email("user@mail.com")
                        .password("kakshechka")
                .build());
        return ResponseEntity.ok("Hello from secured endpoint");
    }
}
