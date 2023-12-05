package com.genesis.commons.utils;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public interface SecurityUtils {

    static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    static Long getUserId() {
        Authentication authentication = getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationCredentialsNotFoundException("Unable to retrieve user ID due to anonymous access");
        } else {
            try {
                return Long.parseLong(authentication.getName());
            } catch (Exception e) {
                throw new AuthenticationCredentialsNotFoundException(
                        "Unable to retrieve user ID while parsing text: %s".formatted(authentication.getName()));
            }
        }
    }

}
