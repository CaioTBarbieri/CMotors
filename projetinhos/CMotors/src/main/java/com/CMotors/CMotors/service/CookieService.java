package com.CMotors.CMotors.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Optional;

public class CookieService {

    public static void setCookie(HttpServletResponse response, String key, String value, int maxAge) throws Exception {
        Cookie cookie = new Cookie(key, URLEncoder.encode(value, "UTF-8"));
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String getCookieValue(HttpServletRequest request, String key) throws Exception {
        String value = Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> key.equals(cookie.getName()))
                        .findAny()
                ).map(e -> e.getValue())
                .orElse(null);
        if (value != null) {
            value = URLDecoder.decode(value, "UTF-8");
            return value;
        }
        return value;
    }
}
