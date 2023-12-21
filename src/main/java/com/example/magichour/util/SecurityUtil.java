package com.example.magichour.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Slf4j
public class SecurityUtil {

    public static Optional<String> getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null) {
            log.info("Security Context에 인증정보가 없습니다.");
            return Optional.empty();
        }

        String userId = null;

        if(authentication.getPrincipal() instanceof UserDetails) {
            log.info("authentication.getPrincipal() instanceof UserDetails");
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            userId = springSecurityUser.getUsername();
            log.info(userId);
        }

        if(authentication.getPrincipal() instanceof String) {
            log.info("authentication.getPrincipal() instanceof String");
            userId = (String) authentication.getPrincipal();
            log.info(userId);
        }

        return Optional.ofNullable(userId);
    }
}
