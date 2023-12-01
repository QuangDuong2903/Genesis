package com.genesis.authservice.config;

import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Map;
import java.util.Set;

@Getter
public class OAuth2PasswordGrantAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    private final String username;
    private final String password;
    private final Set<String> scopes;

    protected OAuth2PasswordGrantAuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                                     Authentication clientPrincipal,
                                                     Map<String, Object> additionalParameters,
                                                     String username, String password, Set<String> scopes) {
        super(OAuth2PasswordGrantAuthenticationConverter.PASSWORD, clientPrincipal, additionalParameters);
        this.username = username;
        this.password = password;
        this.scopes = scopes;
    }
}
