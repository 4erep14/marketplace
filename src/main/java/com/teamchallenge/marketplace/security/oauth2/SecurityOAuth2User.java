package com.teamchallenge.marketplace.security.oauth2;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public abstract class SecurityOAuth2User {
    protected Map<String, Object> attributes;

    public abstract String getName();

    public abstract String getEmail();
}
