package com.teamchallenge.marketplace.config;

//import com.teamchallenge.marketplace.handler.CustomException;
import com.teamchallenge.marketplace.security.oauth2.OAuth2UserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {
    private final UserDetailsService userDetailsService;
    private final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private final static String SECRET_KEY = System.getenv("JWT_SECRET_KEY");

//    @Value("${JWT_SECRET_KEY}")
//    private static final String SECRET_KEY = "4O16YamU9KTl47Qww40nc3GD4SNKwji6gPyM7JG3VdGg2l/H2m1SGgnWhUyp7zVI100ZStvn0I834F9K/mVFFpyjQxbAujhl7UqJC+9mzLI=";


    public String extractUsername(String token) {
        logger.info("Extracting username from JWT token:{}", token);
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        logger.info("Extractin claim from JWT token:{}", token);
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        logger.info("Generated JWT token for user:{}", userDetails.getUsername());
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        logger.info("Generating JWT token with extra claims:{}", extraClaims);
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateTokenForOAuth2User(OAuth2UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        logger.info("Generating JWT token with extra claims:{}", claims);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        logger.info("Retrieving signing key for JWT token generation ");
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        logger.info("Validation JWT token:{}", token);
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        logger.info("Check if JWT token has expired:{}", token);
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        logger.info("Extracting expiration date from JWT token:{}", token);
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        logger.info("Extracting claims from JWT token:{}", token);
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .setAllowedClockSkewSeconds(300)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean validateToken(String token){
        try {
         Jwts.parser().
                 setSigningKey(getSignInKey())
                 .build()
                 .parseClaimsJws(token);

            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }








//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
//        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//    }
//    public String getUsername(String token){
//        return Jwts
//                .parser()
//                .setSigningKey(SECRET_KEY)
//                .build()
//                .parseClaimsJwt(token)
//                .getBody()
//                .getSubject();
//    }


}
