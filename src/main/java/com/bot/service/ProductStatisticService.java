package com.bot.service;


import com.bot.model.Action;
import com.bot.model.dto.StatisticDto;
import com.bot.model.entities.Product;
import com.bot.repositories.ProductRepository;
import com.bot.service.converter.ArgsToEntityConverter;
import com.bot.service.util.CalculateUtils;
import com.bot.service.util.ParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductStatisticService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DataToImageService dataToImageService;

    public File getExtendedInfo(String[] arguments, StringBuilder message, Action action){
        LocalDateTime startDate = LocalDate.now().atStartOfDay().withDayOfMonth(1);
        LocalDateTime endDate = LocalDate.now().plusDays(1).atStartOfDay();
        if (arguments.length == 2) {
            try {
                startDate = ParseUtil.getLocalDateTimeFromString(arguments[0]);
                endDate = ParseUtil.getLocalDateTimeFromString(arguments[1]).plusDays(1);
                if (startDate.isAfter(endDate)) {
                    message.append("Первая дата позднее второй!");
                    return null;
                }
            } catch (DateTimeException e) {
                message.append("Неверный формат дат!(требуется dd-MM-yyyy)");
                return null;
            }
        }

        switch (action){
            case PURCHASES:
                getPurchases(startDate, endDate)
                        .forEach(item -> message.append(item.toString()));
                break;
            case STATISTIC:
                return getStatistic(startDate, endDate, message);
        }

        return null;
    }

    private File getStatistic(LocalDateTime startDate, LocalDateTime endDate, StringBuilder message) {

        BigDecimal total = totalSpend(startDate, endDate);
        List<StatisticDto> statisticData = getStatisticData(startDate, endDate);
        message.append(getStaticticMsg(statisticData, total));
        message.append("Всего потрачено: ").append(total == null ? 0 : total).append(" руб.");
        if (total != null) {
            return dataToImageService.convert(statisticData, startDate.toLocalDate(), endDate.toLocalDate());
        }

        return null;
    }

    private List<Product> getPurchases(LocalDateTime start, LocalDateTime end){
        return productRepository.getAllByDataBetweenAndDeletedFalse(start, end);
    }

    private List<StatisticDto> getStatisticData(LocalDateTime start, LocalDateTime end){
        return productRepository.getStatistic(start, end)
                .stream()
                .map(ArgsToEntityConverter::toStatisticsDto)
                .collect(Collectors.toList());
    }

    private String getStaticticMsg(List<StatisticDto> data, BigDecimal total) {
        StringBuilder str = new StringBuilder();
        data.forEach(price -> str.append(price.toString())
                .append(" (")
                .append(CalculateUtils.getPercent(price.getPrice(), total))
                .append("%)\n"));
        return str.toString();
    }

    private BigDecimal totalSpend(LocalDateTime start, LocalDateTime end){
        return productRepository.getSum(start, end);
    }
}
