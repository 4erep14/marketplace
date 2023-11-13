package com.teamchallenge.marketplace.services.auth;

import com.teamchallenge.marketplace.exception.NullEntityReferenceException;
import com.teamchallenge.marketplace.model.Role;
import com.teamchallenge.marketplace.model.User;
import com.teamchallenge.marketplace.repositories.UserRepository;
import com.teamchallenge.marketplace.security.oauth2.OAuth2UserDetails;
import com.teamchallenge.marketplace.security.oauth2.OAuth2UserDetailsFactory;
import com.teamchallenge.marketplace.security.oauth2.SecurityOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OAuth2UserDetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return checkingOAuth2User(userRequest, user);
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }

    }

    private OAuth2User checkingOAuth2User(OAuth2UserRequest request, OAuth2User oAuth2User) {
        SecurityOAuth2User securityUser = OAuth2UserDetailsFactory.getOAUth2UserDetails(
                request.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );

        if (ObjectUtils.isEmpty(securityUser)) {
            throw new NullEntityReferenceException("Cannot find user");
        }

        Optional<User> user = userRepository.findByEmailAndProviderId(securityUser.getEmail(), request.getClientRegistration().getRegistrationId());

        User userDetail;

        if (user.isPresent()) {
            userDetail = user.get();

            if (!userDetail.getProviderId()
                    .equals(request.getClientRegistration().getRegistrationId())) {
                throw new IllegalArgumentException("Invalid site registration");
            }

            userDetail = updateOAuth2UserDetail(userDetail, securityUser);
        } else {
            userDetail = registerNewOAuth2UserDetail(request, securityUser);
        }

        return new OAuth2UserDetails(
                userDetail.getId(),
                userDetail.getEmail(),
                userDetail.getPassword(),
                Stream.of(userDetail.getRole()).map(r -> new SimpleGrantedAuthority(r.name())).collect(Collectors.toList())
        );
    }

    public User registerNewOAuth2UserDetail(OAuth2UserRequest request, SecurityOAuth2User securityUser) {
        User user = User.builder()
                .email(securityUser.getEmail())
                .providerId(request.getClientRegistration().getRegistrationId())
                .password(passwordEncoder.encode(request.getAccessToken().getTokenValue()))
                .firstName(securityUser.getName().split("\\s")[0])
                .lastName(securityUser.getName().split("\\s")[1])
                .role(Role.USER)
                .build();

        return userRepository.save(user);
    }

    public User updateOAuth2UserDetail(User user, SecurityOAuth2User securityUser) {
        user.setEmail(securityUser.getEmail());
        return userRepository.save(user);
    }
}
