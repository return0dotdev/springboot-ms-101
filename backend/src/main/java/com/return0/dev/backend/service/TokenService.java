package com.return0.dev.backend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.return0.dev.backend.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    @Value("${app.token.private-key}")
    private String privateKey;
    @Value("${app.token.public-key}")
    private String publicKey;
    @Value("${app.token.issuer}")
    private String issuer;


    public String tokenize(User user){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 60);
        Date now = calendar.getTime();

        String token = JWT.create()
                .withIssuer(issuer)
                .withClaim("principal", user.getId())
                .withClaim("role", "USER")
                .withExpiresAt(now)
                .sign(algorithm());

        return token;
    }

    public DecodedJWT verifyToken(String token){
        try {
            JWTVerifier verifier = JWT.require(algorithm())
                    .withIssuer(issuer)
                    .build();

            return verifier.verify(token);
        } catch (Exception e){
            return null;
        }
    }

    private Algorithm algorithm(){
        return Algorithm.HMAC256(privateKey);
    }
}
