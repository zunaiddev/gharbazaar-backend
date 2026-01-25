package com.gharbazaar.backend.dto;

import java.util.Map;

public record CloudinaryRes(
        String publicId,
        String url,
        String secureUrl,
        String format,
        Long bytes,
        Integer width,
        Integer height,
        String resourceType
) {

    public CloudinaryRes(Map data) {
        this(
                (String) data.get("public_id"),
                (String) data.get("url"),
                (String) data.get("secure_url"),
                (String) data.get("format"),
                toLong(data.get("bytes")),
                toInteger(data.get("width")),
                toInteger(data.get("height")),
                (String) data.get("resource_type")
        );
    }

    private static Long toLong(Object value) {
        return value == null ? null : ((Number) value).longValue();
    }

    private static Integer toInteger(Object value) {
        return value == null ? null : ((Number) value).intValue();
    }
}