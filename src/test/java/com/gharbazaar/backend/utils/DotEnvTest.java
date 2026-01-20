package com.gharbazaar.backend.utils;

import org.junit.jupiter.api.Test;

class DotEnvTest {
    private final DotEnv dotEnv = new DotEnv("src/test/resources/application.properties");

    @Test
    void get() {
    }

    @Test
    void getOrDefault() {
    }

    @Test
    void getAll() {
        System.out.println(dotEnv.getAll());
    }
}