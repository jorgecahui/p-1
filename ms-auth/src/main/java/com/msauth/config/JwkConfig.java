package com.msauth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@Configuration
public class JwkConfig {

    @Bean
    public RSAKey rsaJwk() throws Exception {
        return new RSAKeyGenerator(2048)
                .keyUse(com.nimbusds.jose.jwk.KeyUse.SIGNATURE)
                .keyID(UUID.randomUUID().toString())
                .generate();
    }
}

@RestController
@RequestMapping("/auth")
class JwksController {

    private final RSAKey rsaKey;

    public JwksController(RSAKey rsaKey) {
        this.rsaKey = rsaKey;
    }

    @GetMapping("/jwks")
    public Map<String, Object> keys() {
        JWKSet jwkSet = new JWKSet(rsaKey.toPublicJWK());
        return jwkSet.toJSONObject();
    }
}
