package com.teamchallenge.marketplace.services;

import com.teamchallenge.marketplace.dto.auth.ChangePasswordRequest;
import com.teamchallenge.marketplace.model.User;

import java.security.Principal;
import java.util.List;

public interface UserService {
    User create(User user);
    User readById(long id);
    User readByEmail(String email);
    User update(User user);
    void delete(long id);
    List<User> getAll();
    void changePassword(ChangePasswordRequest request, Principal connectedUser);
}
