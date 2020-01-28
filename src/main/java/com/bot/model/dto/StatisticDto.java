package com.bot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatisticDto {
    private String category;
    private int price;

    @Override
    public String toString() {
        return category + " - " + price + "\n";
    }
}
