package com.gharbazaar.backend.filter;

import com.gharbazaar.backend.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

class JwtFilterTest {
    @InjectMocks
    private JwtFilter jwtFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @Mock
    private Claims claims;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doFilterInternal() throws ServletException, IOException {
        Mockito.when(request.getHeader("Authorization"))
                .thenReturn("Bearer token");

        Mockito.when(jwtService.extractClaims(Mockito.anyString()))
                .thenReturn(claims);

        Mockito.when(claims.getSubject()).thenReturn("1");
        Mockito.when(claims.get("email", String.class)).thenReturn("john@gmail.com");
        Mockito.when(claims.get("purpose", String.class)).thenReturn("hjdg");

        jwtFilter.doFilterInternal(request, response, chain);

        Mockito.verifyNoInteractions(chain);
//        Mockito.verify(chain).doFilter(request, response);
    }
}