package com.example.shopmanament.utils;

import com.example.shopmanament.dto.CookieDto;

import java.util.HashMap;
import java.util.Map;

public class JsonParse {
    public static CookieDto parseJsonToCookieDto(String jsonString) {
        CookieDto cookieDto = new CookieDto();
        String[] pairs = jsonString.replaceAll("[{}\"]", "").split(",");
        Map<String, String> keyValuePairs = new HashMap<>();

        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                keyValuePairs.put(key, value);
            }
        }

        if (keyValuePairs.containsKey("username")) {
            cookieDto.setUsername(keyValuePairs.get("username"));
        }
        if (keyValuePairs.containsKey("name")) {
            cookieDto.setName(keyValuePairs.get("name"));
        }
        if (keyValuePairs.containsKey("storeId")) {
            cookieDto.setStoreId(parseLong(keyValuePairs.get("storeId")));
        }
        if (keyValuePairs.containsKey("id")) {
            cookieDto.setId(parseLong(keyValuePairs.get("id")));
        }
        if (keyValuePairs.containsKey("token")) {
            cookieDto.setToken(keyValuePairs.get("token"));
        }
        if (keyValuePairs.containsKey("expireTime")) {
            cookieDto.setExpireTime(parseInt(keyValuePairs.get("expireTime")));
        }
        if (keyValuePairs.containsKey("userId")) {
            cookieDto.setUserId(parseLong(keyValuePairs.get("userId")));
        }

        return cookieDto;
    }

    private static Long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null; // Return null if parsing fails
        }
    }

    private static Integer parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null; // Return null if parsing fails
        }
    }
}
