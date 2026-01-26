package com.gharbazaar.backend.utils;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DotEnv {
    private final Map<String, String> env;

    public DotEnv() {
        this(".env");
    }

    public DotEnv(String filePath) {
        env = new HashMap<>();

        try (FileReader reader = new FileReader(filePath)) {
            List<String> lines = reader.readAllLines();

            lines.forEach(line -> {
                if (line.startsWith("#") || line.isBlank()) return;
                String[] keyValue = line.split("=");
                if (keyValue.length != 2) {
                    env.put(keyValue[0], "");
                    return;
                }

                env.put(keyValue[0], keyValue[1]);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void load() {
        Map<String, String> env = new DotEnv().getAll();

        for (String key : env.keySet()) {
            System.setProperty(key, env.get(key));
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