package com.gharbazaar.backend.filter;

import com.gharbazaar.backend.dto.JwtPayload;
import com.gharbazaar.backend.enums.Purpose;
import com.gharbazaar.backend.enums.UserStatus;
import com.gharbazaar.backend.model.User;
import com.gharbazaar.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
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

import static org.mockito.Mockito.when;

class VerificationFilterTest {
    @InjectMocks
    private VerificationFilter verificationFilter;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doFilterInternal() throws ServletException, IOException {
        JwtPayload payload = new JwtPayload(1L, "john@gmail.com", Purpose.VERIFICATION);
        when(request.getAttribute("payload")).thenReturn(payload);

        User user = new User("John", "john@gmail.com", "John@123", UserStatus.UNVERIFIED);

        when(userService.findById(1L)).thenThrow(new EntityNotFoundException("User not found"));

        verificationFilter.doFilterInternal(request, response, chain);

        Mockito.verifyNoInteractions(chain);
    }
}