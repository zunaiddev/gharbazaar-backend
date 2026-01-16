package com.gharbazaar.backend.utils;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DotEnv {
    private final Map<String, String> env;

    public DotEnv() {
        env = new HashMap<>();

        try (FileReader reader = new FileReader(".env")) {
            List<String> lines = reader.readAllLines();

            lines.forEach(line -> {
                String[] keyValue = line.split("=");
                env.put(keyValue[0], keyValue[1]);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String key) {
        return env.get(key);
    }

    public String getOrDefault(String key, String defaultValue) {
        return env.getOrDefault(key, defaultValue);
    }

    public Map<String, String> getAll() {
        return env;
    }
}