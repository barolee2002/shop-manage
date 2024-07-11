package com.example.shopmanament.dto;

import lombok.Data;

import java.util.Date;

@Data
public class WorkPointDto {
    private String date;
    private String login;
    private String logout;
    public WorkPointDto(String date, String login, String logout) {
        this.date = date;
        this.login= login;
        this.logout = logout;
    }
}
