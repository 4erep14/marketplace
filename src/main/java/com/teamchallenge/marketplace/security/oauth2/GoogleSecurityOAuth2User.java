package com.teamchallenge.marketplace.security.oauth2;

import java.util.Map;

public class GoogleSecurityOAuth2User extends SecurityOAuth2User {

    public GoogleSecurityOAuth2User(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}
