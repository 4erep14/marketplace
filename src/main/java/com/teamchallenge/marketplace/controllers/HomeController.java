package com.teamchallenge.marketplace.controllers;

import com.teamchallenge.marketplace.dto.auth.ChangePasswordRequest;
import com.teamchallenge.marketplace.services.UserService;
import com.teamchallenge.marketplace.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
//@RequestMapping("/api/v1")
public class HomeController {

    private final UserService service;


    @GetMapping("/qwerty")
    @ResponseBody
    public String dratuty(Principal principal){

        System.out.println("I am here");
        return principal.getName();
    }


    @RequestMapping("/login")
    public String sayHello(@AuthenticationPrincipal OAuth2User principal) {
        return "login";
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
