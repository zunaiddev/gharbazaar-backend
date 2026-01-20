package com.gharbazaar.backend.helpers;

import com.gharbazaar.backend.enums.Purpose;
import com.gharbazaar.backend.service.JwtService;
import com.gharbazaar.backend.service.impl.JwtServiceImpl;
import com.gharbazaar.backend.utils.DotEnv;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;

import java.time.Duration;

public class TestHelper {
    public final static String JWT_SECRET;
    private final static JwtService jwtService;

    static {
        DotEnv dotEnv = new DotEnv("src/test/resources/application.properties");
        JWT_SECRET = dotEnv.get("JWT_SECRET");
        jwtService = new JwtServiceImpl(JWT_SECRET);
    }

    public static void printCookie(Cookie cookie) {
        System.out.println("Cookie Info: ");
        System.out.println("\tName: " + cookie.getName());
        System.out.println("\tHttp Only: " + cookie.isHttpOnly());
        System.out.println("\tMax-age: " + cookie.getMaxAge() + " " +
                Duration.ofSeconds(cookie.getMaxAge()).toDays() + " Days");
        System.out.println("\tSecure: " + cookie.getSecure());
    }

    public static boolean validateToken(String token, Purpose purpose) {
        Claims claims = jwtService.extractClaims(token);

        return isValidId(claims.getSubject())
                && claims.get("purpose", String.class)
                .equals(purpose.toString());
    }

    private static boolean isValidId(String id) {
        try {
            return Long.parseLong(id) > 0;
        } catch (NumberFormatException _) {
            return false;
        }
    }
}
