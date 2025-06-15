package com.CMotors.CMotors.service.authenticator;

import com.CMotors.CMotors.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (CookieService.getCookieValue(request, "id") != null) {
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }
}
