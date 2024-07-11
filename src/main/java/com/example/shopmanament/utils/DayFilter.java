package com.example.shopmanament.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum DayFilter {
    WEEK("WEEK"),
    MONTH("MONTH"),
    THREE_MONTH("THREE_MONTH");
    private String typeDay;
    DayFilter(String typeDay) {
        this.typeDay = typeDay;
    }
}
