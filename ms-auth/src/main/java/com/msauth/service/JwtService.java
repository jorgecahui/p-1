package com.msauth.service;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private final RSAKey rsaKey;
    private final long expirationMs = 180_000;

    public JwtService(RSAKey rsaKey) {
        this.rsaKey = rsaKey;
    }

    public String generateToken(String username, Map<String, Object> claims) {
        try {
            JWSSigner signer = new RSASSASigner(rsaKey.toPrivateKey());

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + expirationMs))
                    .claim("roles", claims.get("roles"))
                    .build();

            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                    .keyID(rsaKey.getKeyID())
                    .type(JOSEObjectType.JWT)
                    .build();

            SignedJWT signedJWT = new SignedJWT(header, claimsSet);
            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (Exception e) {
            throw new RuntimeException("Error generando token", e);
        }
    }
}