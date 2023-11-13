package com.teamchallenge.marketplace.security.oauth2;

import com.teamchallenge.marketplace.model.Provider;

import java.util.Map;

public class OAuth2UserDetailsFactory {
    public static SecurityOAuth2User getOAUth2UserDetails(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equals(Provider.google.name())) {
            return new GoogleSecurityOAuth2User(attributes);
        } else if (registrationId.equals(Provider.facebook.name())) {
            return new FacebookSecurityOAuth2User(attributes);
        } else {
            throw new IllegalArgumentException("wrong registration id " + registrationId);
        }
    }
}
