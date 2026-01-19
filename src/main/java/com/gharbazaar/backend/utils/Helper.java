package com.gharbazaar.backend.utils;

import com.gharbazaar.backend.dto.ErrorRes;
import com.gharbazaar.backend.enums.ErrorCode;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;

public class Helper {
    public static void sendErrorRes(HttpServletResponse res, HttpStatus status, ErrorCode code, String message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = res.getWriter();

        res.setContentType("application/json");
        res.setStatus(status.value());
        writer.write(mapper.writeValueAsString(new ErrorRes(status, code, message)));
        writer.flush();
    }

    public static void setRefreshCookie(HttpServletResponse res, String token) {
        Cookie cookie = new Cookie("refreshToken", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) Duration.ofDays(15L).toSeconds());
        cookie.setPath("/");
        cookie.setSecure(true);

        res.addCookie(cookie);
    }


    public static void removeCookie(HttpServletResponse res) {
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setSecure(true);

        res.addCookie(cookie);
    }
}
