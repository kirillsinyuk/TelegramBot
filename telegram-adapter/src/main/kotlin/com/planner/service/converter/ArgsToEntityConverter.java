package com.planner.service.converter;

import com.planner.model.dto.*;
import com.planner.service.entity.*;
import java.math.*;
import org.jfree.data.time.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

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
