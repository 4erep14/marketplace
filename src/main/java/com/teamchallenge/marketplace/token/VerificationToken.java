package com.teamchallenge.marketplace.token;

import com.teamchallenge.marketplace.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table
public class VerificationToken {
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime confirmedAt;
    private static final int EXPIRATION_TIME=15;
    @ManyToOne
    @JoinColumn(nullable = false,name = "user_id")
    private User user;

    public VerificationToken(String token, LocalDateTime expiresAt, LocalDateTime createdAt, User user) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.user = user;
    }
}
