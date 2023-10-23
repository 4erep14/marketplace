package com.teamchallenge.marketplace.controllers;

//import com.teamchallenge.marketplace.model.Customer;
import com.teamchallenge.marketplace.model.User;
import com.teamchallenge.marketplace.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HomeController {

    private final UserService service;

    @GetMapping("/")
    public String sayHello() {
        return "Hello, Home!";
    }

    @GetMapping("/secured")
    public String secured() {
        return "Hello, secured!";
    }
}
