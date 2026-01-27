package com.gharbazaar.backend.controller;

import com.gharbazaar.backend.dto.FormReq;
import com.gharbazaar.backend.dto.FormRes;
import com.gharbazaar.backend.service.FormService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class FormController {
    private final FormService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormRes save(@Valid @RequestBody FormReq req) {
        return service.submit(req);
    }
}
