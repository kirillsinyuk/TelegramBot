package com.bot.model.dto;

import com.bot.model.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class StatisticDataDto {
    private Category category;
    private BigDecimal price;

    @Override
    public String toString() {
        return category + " - " + price;
    }
}
