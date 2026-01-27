package com.gharbazaar.backend.service;

import com.gharbazaar.backend.dto.FormReq;
import com.gharbazaar.backend.dto.FormRes;
import com.gharbazaar.backend.model.Form;

public interface FormService {
    Form save(Form form);

    FormRes submit(FormReq req);
}