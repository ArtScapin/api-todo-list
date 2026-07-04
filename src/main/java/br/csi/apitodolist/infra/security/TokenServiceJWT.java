package br.csi.apitodolist.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenServiceJWT {
    private static final String ACCESS_TOKEN_TYPE = "access";
    private static final String REFRESH_TOKEN_TYPE = "refresh";

    @Value("${auth.jwt.secret}")
    private String secret;

    @Value("${auth.jwt.issuer}")
    private String issuer;

    @Value("${auth.jwt.access-token-expiration-hours}")
    private long accessTokenExpirationHours;

    @Value("${auth.jwt.refresh-token-expiration-days}")
    private long refreshTokenExpirationDays;

    public String getAccessToken(User user) {
        return createToken(user, ACCESS_TOKEN_TYPE, Instant.now().plus(accessTokenExpirationHours, ChronoUnit.HOURS));
    }

    public String getRefreshToken(User user) {
        return createToken(user, REFRESH_TOKEN_TYPE, Instant.now().plus(refreshTokenExpirationDays, ChronoUnit.DAYS));
    }

    private String createToken(User user, String tokenType, Instant expirationDate) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getUsername())
                    .withClaim("ROLE", user.getAuthorities().stream().toList().get(0).toString())
                    .withClaim("token_type", tokenType)
                    .withExpiresAt(expirationDate)
                    .sign(algorithm);
        } catch (JWTCreationException error) {
            throw new RuntimeException("Error to generate JWT Token.", error);
        }
    }

    public String getAccessTokenSubject(String token) {
        return getSubjectByType(token, ACCESS_TOKEN_TYPE);
    }

    public String getRefreshTokenSubject(String token) {
        return getSubjectByType(token, REFRESH_TOKEN_TYPE);
    }

    private String getSubjectByType(String token, String expectedTokenType) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(issuer)
                    .withClaim("token_type", expectedTokenType)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException error) {
            throw new RuntimeException("Invalid JWT Token.");
        }
    }
}
