package com.planner.service.entity;

import com.planner.model.dto.*;
import com.planner.service.chart.*;
import com.planner.service.util.*;
import java.io.*;
import java.math.*;
import java.time.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class StatisticDtoService {

    @Autowired
    private ProductService productService;
    @Autowired
    private PieChartService pieChartService;

    public StatisticDto getStaticticDto(LocalDateTime start, LocalDateTime end, BotUser user){
        BigDecimal totalSpend = totalSpend(start, end, user);
        List<StatisticDataDto> statData = getStatisticData(start, end, user);
        File graphic = totalSpend == null ? null : getStatFile(statData, start, end);

        return StatisticDto.builder()
                .totalSpend(totalSpend)
                .categotySpendings(statData)
                .message(getStaticticMsg(statData, user, totalSpend))
                .statisticFile(graphic)
                .build();
    }

    private BigDecimal totalSpend(LocalDateTime start, LocalDateTime end, BotUser user){
        return productService.getTotalSpend(start, end, user);
    }

    private List<StatisticDataDto> getStatisticData(LocalDateTime start, LocalDateTime end, BotUser user){
        return productService.getStatisticDataDto(start, end, user);
    }

    private File getStatFile(List<StatisticDataDto> data, LocalDateTime start, LocalDateTime end) {
        return pieChartService.create3DPieChart(data, start.toLocalDate(), end.toLocalDate());
    }

    private String getStaticticMsg(List<StatisticDataDto> data, BotUser user, BigDecimal total) {
        StringBuilder str = new StringBuilder();

        str.append(user.getUsername()).append("\n");
        staticticMsg(data, total, str);

        return str.toString();
    }

    public StatisticDto getStaticticDto(LocalDateTime start, LocalDateTime end){
        BigDecimal totalSpend = totalSpend(start, end);
        List<StatisticDataDto> statData = getStatisticData(start, end);
        File graphic = totalSpend == null ? null : getStatFile(statData, start, end);

        return StatisticDto.builder()
                .totalSpend(totalSpend)
                .categotySpendings(statData)
                .message(getStaticticMsg(statData, totalSpend))
                .statisticFile(graphic)
                .build();
    }

    private BigDecimal totalSpend(LocalDateTime start, LocalDateTime end){
        return productService.getTotalSpend(start, end);
    }

    private List<StatisticDataDto> getStatisticData(LocalDateTime start, LocalDateTime end){
        return productService.getStatisticDataDto(start, end);
    }

    private String getStaticticMsg(List<StatisticDataDto> data, BigDecimal total) {
        StringBuilder str = new StringBuilder();

        staticticMsg(data, total, str);

        return str.toString();
    }

    private void staticticMsg(List<StatisticDataDto> data, BigDecimal total, StringBuilder str) {
        data.forEach(dataDto -> str.append(dataDto.getCategory().getName())
                .append(" - ")
                .append(dataDto.getPrice())
                .append(" (")
                .append(CalculateUtils.getPercent(dataDto.getPrice(), total))
                .append("%)\n"));
    }
}
