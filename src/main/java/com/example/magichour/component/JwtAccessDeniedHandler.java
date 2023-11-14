package com.example.magichour.component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*
* 필요한 권한이 존재하지 않는 겨경우에 403 Forbidden 에러를 리턴하는 class
* */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException accessDeniedException) throws IOException {

        // 필요한 권한이 없이 접근하려 할 때 403
        httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
