package com.teamchallenge.marketplace.services.auth;

import com.teamchallenge.marketplace.config.JwtService;
import com.teamchallenge.marketplace.dto.auth.AuthenticationRequest;
import com.teamchallenge.marketplace.dto.auth.AuthenticationResponse;
import com.teamchallenge.marketplace.dto.auth.RegisterRequest;
import com.teamchallenge.marketplace.dto.auth.StoreRegisterRequest;
import com.teamchallenge.marketplace.model.Role;
import com.teamchallenge.marketplace.model.Store;
import com.teamchallenge.marketplace.model.User;
import com.teamchallenge.marketplace.repositories.UserRepository;
import com.teamchallenge.marketplace.repositories.StoreRepository;
import com.teamchallenge.marketplace.security.SecurityUser;
import com.teamchallenge.marketplace.validation.UserValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserValidator<RegisterRequest> userValidator;
    private final UserValidator<StoreRegisterRequest> storeRegisterRequestUserValidator;

    public AuthenticationResponse registerUser(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(new SecurityUser(user));
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse registerStore(StoreRegisterRequest request) {
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                        () -> new EntityNotFoundException("User with email " + request.getEmail() + " not found")
        );
        var store = Store.builder()
                .owner(user)
                .companyName(request.getCompanyName())
                .build();
        storeRepository.save(store);
        var jwtToken = jwtService.generateToken(
                Map.of("company_name", request.getCompanyName()),
                new SecurityUser(user)
        );
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticateCustomer(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var customer = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("User with email " + request.getEmail() + " not found")
        );
        var jwtToken = jwtService.generateToken(new SecurityUser(customer));
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticateStore(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("User with email " + request.getEmail() + " not found")
        );
        var jwtToken = jwtService.generateToken(
                Map.of("company_name", user.getStore().getCompanyName()),
                new SecurityUser(user)
        );
        return new AuthenticationResponse(jwtToken);
    }
    public String validationsRegisterRequest(RegisterRequest registerRequest) {
       userValidator.validate(registerRequest);

        return "";
    }

    public String validationsStore(StoreRegisterRequest storeRegisterRequest) {
        storeRegisterRequestUserValidator.validate(storeRegisterRequest);
        return "";
    }
    public String throwException(){
        throw new IllegalStateException("Some exception happened");
    }

}
