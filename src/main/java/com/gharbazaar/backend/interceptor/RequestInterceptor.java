package com.gharbazaar.backend.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;
import java.util.Collections;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    private static final String START_TIME = "startTime";
    // ANSI color codes
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String RESET = "\u001B[0m";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        request.setAttribute(START_TIME, Instant.now().toEpochMilli());

        System.out.println("\n🔵 Incoming Request");
        System.out.println("➡ Method      : " + request.getMethod());
        System.out.println("➡ URI         : " + request.getRequestURI());
        System.out.println("➡ Query       : " + request.getQueryString());
        System.out.println("➡ Remote IP  : " + request.getRemoteAddr());

        System.out.println("➡ Headers:");
        Collections.list(request.getHeaderNames())
                .forEach(h -> System.out.println("   " + h + ": " + request.getHeader(h)));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        long startTime = (long) request.getAttribute(START_TIME);
        long duration = Instant.now().toEpochMilli() - startTime;

        int status = response.getStatus();

        String color = getColorByStatus(status);

        System.out.println(color + "⬅ Response");
        System.out.println("⬅ Status     : " + status);
        System.out.println("⬅ Duration   : " + duration + " ms");
        System.out.println("⬅ ContentType: " + response.getContentType());
        System.out.println(RESET);
    }

    private String getColorByStatus(int status) {
        if (status >= 200 && status < 300) return GREEN;
        if (status >= 400 && status < 500) return YELLOW;
        if (status >= 500) return RED;
        return BLUE;
    }
}
