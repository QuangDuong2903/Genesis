package com.genesis.authservice.config;

import com.genesis.authservice.utils.OAuth2Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Converter for OAuth2 password grant type. This converter is used to convert a request to an authentication object.
 * Spring Authorization Server does not provide a converter for this grant type. So we have to implement it on our own.
 */
public class OAuth2PasswordGrantAuthenticationConverter implements AuthenticationConverter {

    public static final AuthorizationGrantType PASSWORD = new AuthorizationGrantType("password");

    @Override
    public Authentication convert(HttpServletRequest request) {

        // grant_type (REQUIRED)
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!PASSWORD.getValue().equals(grantType))
            return null;

        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();

        if (clientPrincipal == null)
            OAuth2Utils.throwError(OAuth2ErrorCodes.INVALID_CLIENT, OAuth2ParameterNames.CLIENT_ID,
                    OAuth2Utils.ACCESS_TOKEN_REQUEST_ERROR_URI);

        MultiValueMap<String, String> parameters = OAuth2Utils.getParameters(request);

        // username (REQUIRED)
        String username = parameters.getFirst(OAuth2ParameterNames.USERNAME);

        // password (REQUIRED)
        String password = parameters.getFirst(OAuth2ParameterNames.PASSWORD);

        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)
                || parameters.get(OAuth2ParameterNames.USERNAME).size() != 1
                || parameters.get(OAuth2ParameterNames.PASSWORD).size() != 1)
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_REQUEST);

        // scope (OPTIONAL)
        String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
        if (scope != null && !StringUtils.hasText(scope) && parameters.get(OAuth2ParameterNames.SCOPE).size() != 1)
            OAuth2Utils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.SCOPE,
                    OAuth2Utils.ACCESS_TOKEN_REQUEST_ERROR_URI);

        Set<String> scopes = scope != null ? Set.of(scope.split(" ")) : null;

        Map<String, Object> additionalParameters = new HashMap<>();
        parameters.forEach((key, value) -> {
            if (!key.equals(OAuth2ParameterNames.GRANT_TYPE) &&
                    !key.equals(OAuth2ParameterNames.SCOPE)
            ) additionalParameters.put(key, value.get(0));
        });

        return new OAuth2PasswordGrantAuthenticationToken(
                OAuth2PasswordGrantAuthenticationConverter.PASSWORD,
                clientPrincipal,
                additionalParameters,
                username,
                password,
                scopes
        );
    }
}
