package com.baeldung.newstack.spring;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

public class CustomClaimsValidator implements OAuth2TokenValidator<Jwt> {

    OAuth2Error error = new OAuth2Error("invalid_token", "Unsupported email domain", null);

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        String preferredUsername = jwt.getClaimAsString("preferred_username");
        if (preferredUsername.endsWith("@baeldung.com")) {
            return OAuth2TokenValidatorResult.success();
        } else {
            return OAuth2TokenValidatorResult.failure(error);
        }
    }
}
