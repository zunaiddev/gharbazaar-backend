package com.gharbazaar.backend;

import com.gharbazaar.backend.model.UsedToken;
import com.gharbazaar.backend.repository.UsedTokenRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransectionalTest {
    @Autowired
    private UsedTokenRepository repository;

    @Test
    @Order(1)
    void testSaveToken() {
        repository.save(new UsedToken(null, "token1"));
        repository.save(new UsedToken(null, "token2"));
        repository.save(new UsedToken(null, "token3"));
        System.out.println("End");
    }

    @Test
    @Order(2)
    void testGetToken() {
        UsedToken t1 = repository.findByToken("token1").orElse(null);
        assertNotNull(t1);

        UsedToken t2 = repository.findByToken("token1").orElse(null);
        assertNotNull(t2);

        UsedToken t3 = repository.findByToken("token1").orElse(null);
        assertNotNull(t3);
    }
}
