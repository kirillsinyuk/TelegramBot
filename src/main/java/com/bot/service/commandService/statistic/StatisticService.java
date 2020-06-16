package com.bot.service.commandService.statistic;

import com.bot.model.dto.StatisticDataDto;
import com.bot.model.menu.stats.CurrentTimePeriod;
import com.bot.repositories.ProductRepository;
import com.bot.service.DataToImageService;
import com.bot.service.converter.ArgsToEntityConverter;
import com.bot.service.keyboard.StatsKeyboardService;
import com.bot.service.util.CalculateUtils;
import com.bot.service.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticService {

//    @Autowired
//    private ProductRepository productRepository;
//    @Autowired
//    private DataToImageService dataToImageService;
//    @Autowired
//    private StatsKeyboardService<CurrentTimePeriod> statsKeyboardService;
//
//    public File getStatistic(String[] arguments, StringBuilder message){
//        switch (arguments.length) {
//            case 1:
//                return getStatistic(getStartDate(arguments[0]), LocalDate.now().plusDays(1).atStartOfDay(), message);
//            case 2:
//                LocalDateTime startDate;
//                LocalDateTime endDate;
//                try {
//                    startDate = ParseUtil.getLocalDateTimeFromString(arguments[0]);
//                    endDate = ParseUtil.getLocalDateTimeFromString(arguments[1]).plusDays(1);
//                    if (startDate.isAfter(endDate)) {
//                        message.append("Первая дата позднее второй!");
//                        return null;
//                    }
//                } catch (DateTimeException e) {
//                    message.append("Неверный формат дат!(требуется dd-MM-yyyy)");
//                    return null;
//                }
//                return getStatistic(startDate, endDate, message);
//            default:
//                return null;
//        }
//    }
//
//    private File getStatistic(LocalDateTime startDate, LocalDateTime endDate, StringBuilder message) {
//        BigDecimal total = totalSpend(startDate, endDate);
//        List<StatisticDataDto> statisticData = getStatisticData(startDate, endDate);
//        message.append(getStaticticMsg(statisticData, total));
//        message.append("Всего потрачено: ").append(total == null ? 0 : total).append(" руб.");
//        if (total != null) {
//            return dataToImageService.convert(statisticData, startDate.toLocalDate(), endDate.toLocalDate());
//        }
//
//        return null;
//    }
//
//    private List<StatisticDataDto> getStatisticData(LocalDateTime start, LocalDateTime end){
//        return productRepository.getStatistic(start, end)
//                .stream()
//                .map(ArgsToEntityConverter::toStatisticsDto)
//                .collect(Collectors.toList());
//    }
//
//    private String getStaticticMsg(List<StatisticDataDto> data, BigDecimal total) {
//        StringBuilder str = new StringBuilder();
//        data.forEach(price -> str.append(price.toString())
//                .append(" (")
//                .append(CalculateUtils.getPercent(price.getPrice(), total))
//                .append("%)\n"));
//        return str.toString();
//    }
//
//    private BigDecimal totalSpend(LocalDateTime start, LocalDateTime end){
//        return productRepository.getSum(start, end);
//    }
//
//    public LocalDateTime getStartDate(String period){
//        switch (period){
//            case "today":
//                return LocalDate.now().atStartOfDay();
//            case "week":
//                return LocalDate.now().atStartOfDay().minusWeeks(1);
//            case "month":
//                return LocalDate.now().atStartOfDay().withDayOfMonth(1);
//            case "full":
//                return LocalDate.of(2020, 2, 6).atStartOfDay();
//            default:
//                return LocalDate.now().atStartOfDay().withDayOfMonth(1);
//        }
//    }

}
