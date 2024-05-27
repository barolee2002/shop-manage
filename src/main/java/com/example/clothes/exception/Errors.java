package com.example.clothes.exception;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public enum Errors {

    USER_NOT_FOUND("USER NOT FOUND", HttpStatus.NOT_FOUND.value()),
    NOT_FOUND("NOT FOUND", HttpStatus.NOT_FOUND.value()),
    INVALID_DATA("INVALID DATA", HttpStatus.BAD_REQUEST.value()),
    EXIST_USER( "EXIST USER", HttpStatus.BAD_REQUEST.value()),
    INCORRECT_FORMAT("INCORRECT FORMAT", HttpStatus.BAD_REQUEST.value()),
    EXIST_ATTRIBUTE("EXIST ATTRIBUTE", HttpStatus.BAD_REQUEST.value()),
    NOT_PERMITTED("not have permitted", HttpStatus.BAD_REQUEST.value());

    private String errorDes;
    private int status;

    Errors(String errorDes, int status) {
        this.errorDes = errorDes;
        this.status = status;
    }
}
