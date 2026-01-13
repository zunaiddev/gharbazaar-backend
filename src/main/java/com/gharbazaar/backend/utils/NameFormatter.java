package com.gharbazaar.backend.utils;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

public class NameFormatter extends ValueDeserializer<String> {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx) {
        String name = p.getValueAsString();

        if (name == null) return null;

        name = name.trim().replaceAll("\\s+", " ").toLowerCase();
        StringBuilder formatedName = new StringBuilder();

        for (String word : name.split(" ")) {
            formatedName.append(word.substring(0, 1).toUpperCase())
                    .append(word.substring(1))
                    .append(" ");
        }

        return formatedName.toString().trim();
    }
}
