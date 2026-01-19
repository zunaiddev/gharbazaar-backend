package com.gharbazaar.backend.helpers;

import jakarta.servlet.http.Cookie;

import java.time.Duration;

public class TestHelper {
    public static void printCookie(Cookie cookie) {
        System.out.println("Cookie Info: ");
        System.out.println("\tName: " + cookie.getName());
        System.out.println("\tHttp Only: " + cookie.isHttpOnly());
        System.out.println("\tMax-age: " + cookie.getMaxAge() + " " +
                Duration.ofSeconds(cookie.getMaxAge()).toDays() + " Days");
        System.out.println("\tSecure: " + cookie.getSecure());
    }
}
