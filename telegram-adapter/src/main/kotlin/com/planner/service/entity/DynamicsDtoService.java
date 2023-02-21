package com.planner.service.entity;

import com.planner.model.dto.*;
import com.planner.service.chart.*;
import java.io.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class DynamicsDtoService {

    @Autowired
    private ProductService productService;
    @Autowired
    private DynamicChartService dynamicChartService;

    public DynamicsDto getDynamicsDto(Set<BotUser> users){
        BigDecimal totalSpend = totalSpend(users);
        Map<String, List<DynamicsDataDto>> dynData = getDynamicsDataUserMap(users);
        File graphic = totalSpend.equals(BigDecimal.ZERO) ? null : getDynamicsFile(dynData);

        return DynamicsDto.builder()
                .totalSpend(totalSpend)
                .monthSpendings(dynData)
                .message(getStaticticMsg(totalSpend))
                .dynamicsFile(graphic)
                .build();
    }

    private BigDecimal totalSpend(Set<BotUser> users){
        return users.stream().map(usr -> productService.getTotalSpendAllTime(usr)).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Map<String, List<DynamicsDataDto>> getDynamicsDataUserMap(Set<BotUser> user){
        return user.stream()
                .map(usr -> new AbstractMap.SimpleImmutableEntry<>(usr.getUsername(), productService.getDynamicsDataDto(usr)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private File getDynamicsFile(Map<String, List<DynamicsDataDto>> dynData) {
        return dynamicChartService.createDynamicChart(dynData);
    }

    private String getStaticticMsg(BigDecimal total) {
        return String.format("Всего потрачено %s руб.", total.intValue());
    }
}
