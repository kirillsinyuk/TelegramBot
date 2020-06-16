package com.bot.service.entity;

import com.bot.model.dto.StatisticDataDto;
import com.bot.model.dto.StatisticDto;
import com.bot.model.entities.BotUser;
import com.bot.service.DataToImageService;
import com.bot.service.util.CalculateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatisticDtoService {

    @Autowired
    private ProductService productService;
    @Autowired
    private DataToImageService dataToImageService;

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
        return dataToImageService.convert(data, start.toLocalDate(), end.toLocalDate());
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
