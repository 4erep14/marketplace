package com.teamchallenge.marketplace.services.impl;

import com.teamchallenge.marketplace.dto.auth.ChangePasswordRequest;
import com.teamchallenge.marketplace.model.User;
import com.teamchallenge.marketplace.repositories.UserRepository;
import com.teamchallenge.marketplace.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User create(User user) {
        if(user !=null) {
            return userRepository.save(user);
        } else {
            throw new NullPointerException("Seller cannot be null");
        }
    }

    @Override
    public User readById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found")
        );
    }

    @Override
    public User readByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("User with email " + " not found")
        );
    }

    @Override
    public User update(User user) {
        if (user != null) {
            return userRepository.save(user);
        }
        throw new NullPointerException("User cannot be 'null'");
    }

    @Override
    public void delete(long id) { userRepository.delete(readById(id)); }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user =((User)((UsernamePasswordAuthenticationToken)connectedUser).getPrincipal());
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())){
            throw new IllegalStateException("Password are not the same");
        }
        //update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        //new password
        userRepository.save(user);
    }

}
