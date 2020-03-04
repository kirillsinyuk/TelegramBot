package com.bot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class StatisticDto {
    private String category;
    private BigDecimal price;

    @Override
    public String toString() {
        return category + " - " + price;
    }
}
