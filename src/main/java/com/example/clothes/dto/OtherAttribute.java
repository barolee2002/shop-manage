package com.example.clothes.dto;

import lombok.Data;

@Data
public class OtherAttribute {
    private String name;
    private String value;
    @Override
    public String toString() {
        return "{" + this.name + "," + this.value + "}";
    }

}
