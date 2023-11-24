package com.teamchallenge.marketplace.services;

import com.teamchallenge.marketplace.dto.auth.ChangePasswordRequest;
import com.teamchallenge.marketplace.exception.NullEntityReferenceException;
import com.teamchallenge.marketplace.model.User;
import com.teamchallenge.marketplace.repositories.UserRepository;
//import com.teamchallenge.marketplace.util.OtpUtil;
import com.teamchallenge.marketplace.repositories.VerificationTokenRepository;
import com.teamchallenge.marketplace.token.VerificationToken;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.plaf.UIResource;
import java.security.Principal;
import java.util.Calendar;
import java.util.List;

@Service
public class UserService  {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, VerificationTokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
    }


    public User create(User user) {
        if(user !=null) {
            logger.info("Creating user:{}", user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);

        } else {
            throw new NullEntityReferenceException("Seller cannot be null");
        }
    }

    public User readById(long id) {
        logger.info("Retrieving user with ID: {}", id);
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found")
        );
    }

    public User readByEmail(String email) {
        logger.info("Retrieving user with email: {}", email);
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User with email " + " not found")
        );

    }

    public User update(User user) {
        if (user != null) {
            logger.info("Update user: {}", user);
            return userRepository.save(user);
        }
        throw new NullEntityReferenceException("User cannot be 'null'");
    }

    public void delete(long id) {
        logger.info("Deleting user with ID: {}", id);
        userRepository.delete(readById(id));
    }

    public List<User> getAll() {
        logger.info("Retrieving all users");
        return userRepository.findAll();
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        logger.info("User is requesting to change to password");
        var user =((User)((UsernamePasswordAuthenticationToken)connectedUser).getPrincipal());
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            logger.error("Incorrect password provided");
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())){
            logger.error("New passwords do not match");
            throw new IllegalStateException("Password are not the same");
        }
        //update the password
        logger.info("Updating user`s password");
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        //new password
        userRepository.save(user);
    }
    public int enableUser(String email){
        return userRepository.enableUser(email);
    }
    





}
