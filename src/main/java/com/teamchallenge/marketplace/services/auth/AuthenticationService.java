package com.teamchallenge.marketplace.services.auth;

import com.teamchallenge.marketplace.config.JwtService;
import com.teamchallenge.marketplace.dto.auth.*;
import com.teamchallenge.marketplace.email.EmailSender;
import com.teamchallenge.marketplace.model.Provider;
import com.teamchallenge.marketplace.model.Role;
import com.teamchallenge.marketplace.model.Store;
import com.teamchallenge.marketplace.model.User;
import com.teamchallenge.marketplace.repositories.UserRepository;
import com.teamchallenge.marketplace.repositories.StoreRepository;
import com.teamchallenge.marketplace.repositories.VerificationTokenRepository;
import com.teamchallenge.marketplace.security.SecurityUser;
import com.teamchallenge.marketplace.services.ConfirmationTokenService;
import com.teamchallenge.marketplace.services.UserService;
import com.teamchallenge.marketplace.token.VerificationToken;
import com.teamchallenge.marketplace.validation.EmailValidator;
import com.teamchallenge.marketplace.validation.UserValidator;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthenticationService  {


    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserValidator<RegisterRequest> userValidator;
    private final UserValidator<StoreRegisterRequest> storeRegisterRequestUserValidator;
    private final EmailValidator emailValidator;
    private final UserService userService;
    private final VerificationTokenRepository tokenRepository;
    private final ConfirmationTokenService tokenService;
    private final EmailSender emailSender;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationResponse registerUser(RegisterRequest request) throws MessagingException {
        logger.info("Registering new user with email:{}", request.getEmail());
        userValidator.validate(request);

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(Role.USER)
                .providerId(Provider.local.name())
                .build();

        userRepository.save(user);
        String token =UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user);
        tokenService.saveVerifyToken(verificationToken);
        String link = "http://localhost:9090/api/v1/register/confirm?token="+token;
        emailSender.send(request.getEmail(),buildEmail(request.getFirstName(), link) );

        var jwtToken = jwtService.generateToken(new SecurityUser(user));
        logger.info("Generated JWT token for user:{}", user.getEmail());
        return new AuthenticationResponse(jwtToken);
    }



    public AuthenticationResponse registerStore(StoreRegisterRequest request) {
        logger.info("Registering new store with email:{}", request.getEmail());
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                        () -> new EntityNotFoundException("User with email " + request.getEmail() + " not found")
        );
        var store = Store.builder()
                .owner(user)
                .companyName(request.getCompanyName())
                .build();
        storeRepository.save(store);
        var jwtToken = jwtService.generateToken(
                Map.of("company_name", request.getCompanyName()),
                new SecurityUser(user)
        );
        logger.info("Generated JWT token for store:{}", store.getCompanyName());
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticateUser(AuthenticationRequest request) {
        logger.info("Authenticating  customer with email:{}", request.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var customer = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("User with email " + request.getEmail() + " not found")
        );
        var jwtToken = jwtService.generateToken(new SecurityUser(customer));
        logger.info("Generated JWT token for store:{}", customer.getEmail());
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticateStore(AuthenticationRequest request) {
        logger.info("Authenticating store with email:{}", request.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("User with email " + request.getEmail() + " not found")
        );
        var jwtToken = jwtService.generateToken(
                Map.of("company_name", user.getStore().getCompanyName()),
                new SecurityUser(user)
        );
        logger.info("Generated JWT token for store:{}", user.getStore().getCompanyName());
        return new AuthenticationResponse(jwtToken);
    }
    public String validationsRegisterRequest(RegisterRequest registerRequest) {
        logger.info("Validating user with email:{}", registerRequest.getEmail());
        userValidator.validate(registerRequest);
        logger.info("Registration request validation succesful");
        return "";
    }

    public String validationsStore(StoreRegisterRequest storeRegisterRequest) {
        logger.info("Validating store with email:{}", storeRegisterRequest.getEmail());
        storeRegisterRequestUserValidator.validate(storeRegisterRequest);
        logger.info("Store registration request validation succesful");
        return "";
    }
    public String validationEmail(RegisterRequest request){
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        return "";

    }



//    public String login(LoginDto loginDto) {
//        User user = userRepository.findByEmail(loginDto.getEmail())
//                .orElseThrow(
//                        () -> new RuntimeException("User not found with this email: " + loginDto.getEmail()));
//        if (!loginDto.getPassword().equals(user.getPassword())) {
//            return "Password is incorrect";
//        } else if (!user.isActive()) {
//            return "your account is not verified";
//        }
//        return "Login successful";
//    }
    public String confirmToken(String token){
        VerificationToken verificationToken = tokenService
                .getToken(token)
                .orElseThrow(()->
                        new IllegalStateException("token not found"));
        if (verificationToken.getConfirmedAt()!=null){
            throw new IllegalStateException("email already confirmed");
        }
        LocalDateTime expriredAt = verificationToken.getExpiresAt();
        if (expriredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }
        tokenService.setConfirmedAt(token);
        userService.enableUser(verificationToken.getUser().getEmail());
        return "confirmed";

    }

    private String buildEmail(String name, String link) {

        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }



    public String throwException(){

        throw new IllegalStateException("Some exception happened");
    }


}
