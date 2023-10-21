package com.teamchallenge.marketplace.security;

import com.teamchallenge.marketplace.model.Seller;
import com.teamchallenge.marketplace.model.User;

public class SecuritySeller extends SecurityUser {
    public SecuritySeller(User user) {
        super(user);
    }

    public static SecuritySeller fromSeller(Seller seller) {
        return new SecuritySeller(seller);
    }
}
