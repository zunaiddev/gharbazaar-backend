package com.gharbazaar.backend;

import com.gharbazaar.backend.dto.ErrorRes;
import com.gharbazaar.backend.enums.EmailAlia;
import com.gharbazaar.backend.enums.ErrorCode;
import com.gharbazaar.backend.utils.ReferenceIdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    void durationTest() {
        System.out.println(Duration.ofDays(15L).toSeconds());
    }

    @Test
    void directoryTest() {
        try {
            Path path = Paths.get("src/main/resources/avatars");

            if (Files.notExists(path)) {
                Files.createDirectories(path);
                System.out.println("Directory Created");
            }

            System.out.println("Directory Exists Status: " + Files.exists(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test() {
        System.out.println(EmailAlia.NO_REPLY.getAlias());
    }

    @Test
    void generate() {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            list.add(ReferenceIdGenerator.generate());
        }

        for (int i = 0; i < list.size(); i++) {
            String element = list.get(i);

            for (int j = 0; j < 100000; j++) {
                if (i == j) continue;

                if (element.equals(list.get(j))) {
                    System.out.println("Element Found: " + element);
                }
            }
        }
    }
}
