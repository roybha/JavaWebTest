package org.example;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CookieCheck {
    public static final List<String> cookies = new ArrayList<>(List.of("userId","password","login"));
    private static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        return Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals(name)).findFirst();
    }
    public static Optional<List<Cookie>> getAuthCookies(HttpServletRequest request) {
        List<Cookie> authCookies = new ArrayList<>();
        cookies.forEach(cookieName->getCookie(request, cookieName).ifPresent(authCookies::add));
        return Optional.of(authCookies);
    }

}
