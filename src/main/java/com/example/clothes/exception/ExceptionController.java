package com.example.clothes.exception;

import com.example.clothes.dto.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = AppException.class)
    public Response<Object> handleException(AppException exception){
        return new Response<>(exception.getStatus(), exception.getErrorDes());
    }
}
