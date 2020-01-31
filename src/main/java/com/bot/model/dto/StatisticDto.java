package com.bot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class StatisticDto {
    private String category;
    private BigDecimal price;

    @Override
    public String toString() {
        return category + " - " + price;
    }
}
