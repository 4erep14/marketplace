package com.teamchallenge.marketplace.services.auth;

import com.teamchallenge.marketplace.config.JwtService;
import com.teamchallenge.marketplace.dto.auth.AuthenticationRequest;
import com.teamchallenge.marketplace.dto.auth.AuthenticationResponse;
import com.teamchallenge.marketplace.dto.auth.CustomerRegisterRequest;
import com.teamchallenge.marketplace.dto.auth.SellerRegisterRequest;
import com.teamchallenge.marketplace.model.Customer;
import com.teamchallenge.marketplace.model.Role;
import com.teamchallenge.marketplace.model.Seller;
import com.teamchallenge.marketplace.repositories.CustomerRepository;
import com.teamchallenge.marketplace.repositories.SellerRepository;
import com.teamchallenge.marketplace.security.SecurityUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerCustomer(CustomerRegisterRequest request) {
        var customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        customerRepository.save(customer);
        var jwtToken = jwtService.generateToken(new SecurityUser(customer));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerSeller(SellerRegisterRequest request) {
        var seller = Seller.builder()
                .companyName(request.getCompanyName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        sellerRepository.save(seller);
        var jwtToken = jwtService.generateToken(new SecurityUser(seller));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticateCustomer(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var customer = customerRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("User with email " + request.getEmail() + " not found")
        );
        var jwtToken = jwtService.generateToken(new SecurityUser(customer));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticateSeller(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var seller = sellerRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("User with email " + " not found")
        );
        var jwtToken = jwtService.generateToken(new SecurityUser(seller));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
