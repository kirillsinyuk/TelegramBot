package com.bot.service.converter;

import com.bot.model.dto.DynamicsDataDto;
import com.bot.model.entities.Category;
import com.bot.model.dto.StatisticDataDto;
import com.bot.service.entity.CategoryService;
import org.jfree.data.time.Month;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class ArgsToEntityConverter {


    @Autowired
    private CategoryService categoryService;

    public StatisticDataDto toStatisticsDto(Object[] obj){
        Category category = categoryService.getCategoryById(((BigInteger)obj[0]).longValue());
        BigDecimal price = (BigDecimal)obj[1];

        return new StatisticDataDto(category, price);
    }

    public DynamicsDataDto toDynamicsDto(Object[] obj, String userName){
        Month month = new Month((int)obj[0], (int)obj[1]);
        BigDecimal price = (BigDecimal)obj[2];

        return new DynamicsDataDto(month, price, userName);
    }
}
