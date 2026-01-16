package com.gharbazaar.backend;

import com.gharbazaar.backend.dto.ErrorRes;
import com.gharbazaar.backend.enums.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import tools.jackson.databind.ObjectMapper;

class BackendApplicationTests {

    @Test
    void basicTest() {
        ObjectMapper mapper = new ObjectMapper();

        ErrorRes errorRes = new ErrorRes(HttpStatus.OK, ErrorCode.INTERNAL_SERVER_ERROR, "Hii This is wrong");

        System.out.println(mapper.writeValueAsString(errorRes));
    }

    @Test
    void ErrorResTest() {
        ErrorRes res1 = new ErrorRes(HttpStatus.FORBIDDEN, ErrorCode.DISABLED, "User is Disabled");
        ErrorRes res2 = new ErrorRes(HttpStatus.FORBIDDEN, "User is Not Allowed");
        ErrorRes res3 = new ErrorRes(HttpStatus.FORBIDDEN);

        System.out.println(res1);
        System.out.println(res2);
        System.out.println(res3);
    }

}
