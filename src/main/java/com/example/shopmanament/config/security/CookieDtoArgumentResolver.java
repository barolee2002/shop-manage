package com.example.shopmanament.config.security;

import com.example.shopmanament.dto.CookieDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component
public class CookieDtoArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Logger logger = LoggerFactory.getLogger(CookieDtoArgumentResolver.class);
    private final ObjectMapper objectMapper;

    public CookieDtoArgumentResolver(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(CookieDto.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String cookie = webRequest.getHeader("Cookie");
        logger.debug("Received cookies: {}", cookie);
        if (cookie != null) {
            String[] cookies = cookie.split(";");
            for (String c : cookies) {
                String[] cookiePair = c.trim().split("=", 2);
                if ("userInfo".equals(cookiePair[0]) && cookiePair.length == 2) {
                    String decodedValue = URLDecoder.decode(cookiePair[1], StandardCharsets.UTF_8.name());
                    logger.debug("Found userInfo cookie: {}", decodedValue);
                    return objectMapper.readValue(decodedValue, CookieDto.class);
                }
            }
        }
        logger.debug("userInfo cookie not found or invalid");
        return null;
    }
}
