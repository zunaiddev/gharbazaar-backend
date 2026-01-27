package com.gharbazaar.backend.service.impl;

import com.gharbazaar.backend.dto.FormReq;
import com.gharbazaar.backend.dto.FormRes;
import com.gharbazaar.backend.model.Form;
import com.gharbazaar.backend.repository.FormRepository;
import com.gharbazaar.backend.service.FormService;
import com.gharbazaar.backend.utils.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {
    private final FormRepository repo;
    private final EmailSender emailSender;

    @Override
    public Form save(Form form) {
        if (form.getId() != null) {
            throw new IllegalStateException("Form id is auto generated");
        }

        return repo.save(form);
    }

    @Override
    public FormRes submit(FormReq req) {
        Form form = repo.save(new Form(req));

        emailSender.sendFormEmail(form);
        emailSender.sendAdminFormEmail(form, "work87t@gmail.com");
        emailSender.sendAdminFormEmail(form, "ja7667924@gmail.com");

        return new FormRes(form);
    }
}