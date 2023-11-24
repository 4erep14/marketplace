package com.teamchallenge.marketplace.services;

import com.teamchallenge.marketplace.repositories.VerificationTokenRepository;
import com.teamchallenge.marketplace.token.VerificationToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;
    public void saveVerifyToken(VerificationToken token){
        verificationTokenRepository.save(token);
    }
    public Optional<VerificationToken> getToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return verificationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
