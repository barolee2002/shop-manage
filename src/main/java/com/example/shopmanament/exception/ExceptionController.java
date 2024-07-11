package com.example.shopmanament.exception;

import com.example.shopmanament.dto.Response;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = AppException.class)
    public Response<ErrorResponse> handleException(AppException exception){
        return new Response<>(exception.getStatus(), exception.getErrorDes());
    }
}
