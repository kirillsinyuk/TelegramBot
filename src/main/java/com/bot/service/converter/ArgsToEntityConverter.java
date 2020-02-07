package com.bot.service.converter;


import com.bot.model.Category;
import com.bot.model.dto.StatisticDto;
import com.bot.model.entities.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ArgsToEntityConverter {

    public static Product toProduct(String[] arguments, int price, String username) throws IllegalArgumentException {
        String desc = arguments.length >= 3 ?
                Arrays.stream(arguments)
                        .skip(2)
                        .collect(Collectors.joining(" "))
                : null;

        if (!Category.containsCategory(arguments[0])) {
            throw new IllegalArgumentException();
        }

        return new Product(Category.getCategoryByName(arguments[0]), price, LocalDateTime.now(), desc, username);
    }

    public static StatisticDto toStatisticsDto(Object[] obj){
        String category = Category.getNameByCategory((String)obj[0]);
        BigDecimal price = (BigDecimal)obj[1];

        return new StatisticDto(category, price);
    }
}
