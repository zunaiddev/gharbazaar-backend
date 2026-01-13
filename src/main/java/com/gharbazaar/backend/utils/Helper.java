package com.gharbazaar.backend.utils;

import com.gharbazaar.backend.dto.ErrorRes;
import com.gharbazaar.backend.enums.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class Helper {
    public void sendErrorRes(HttpServletResponse res, HttpStatus status, ErrorCode code, String message) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = res.getWriter();

        res.setContentType("application/json");
        res.setStatus(status.value());
        writer.write(mapper.writeValueAsString(new ErrorRes(status, code, message)));
        writer.flush();
    }
}
