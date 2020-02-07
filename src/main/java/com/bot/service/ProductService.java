package com.bot.service;

import com.bot.model.Action;
import com.bot.model.Category;
import com.bot.model.dto.StatisticDto;
import com.bot.model.entities.Product;
import com.bot.repositories.ProductRepository;
import com.bot.service.converter.ArgsToEntityConverter;
import com.bot.service.util.CalculateUtils;
import com.bot.service.util.ParseUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import java.io.File;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Logger LOG;


    public boolean commonAction(String[] arguments, User user, StringBuilder message, Action action) {
        try {
            if (arguments.length < 2) {
                throw new NumberFormatException();
            }
            int price = Integer.parseInt(arguments[1]);

            switch (action){
                case ADD:
                    createAndSaveProduct(arguments, user, price, message);
                    break;
                case DELETE:
                    deleteProduct(arguments, price, message);
                    break;
                case PURCHASES:
                    getAllPurchases(arguments, message);
                    break;
            }
            return true;

        } catch (NumberFormatException e){
            message.append("Неверный формат команды! Формат:\n /add &lt;category&gt; &lt;price&gt; &lt;description&gt;");
            LOG.error(String.format("Неверный формат ввода от пользователя %s id: %d", user.getFirstName(), user.getId()), e);

        } catch (IllegalArgumentException e){
            message.append("Категория не найдена! Список доступных категорий:\n");
            Arrays.stream(Category.values()).forEach(x -> message.append(x.getName()).append("\n"));
            LOG.error(String.format("Попытка добавления неизвестной категории от пользователя %s id: %d", user.getFirstName(), user.getId()), e);
        }
        return false;
    }

    private void getAllPurchases(String[] arguments, StringBuilder message) throws NumberFormatException {
        try {
            LocalDateTime startDate = ParseUtil.getLocalDateTimeFromString(arguments[0]);
            LocalDateTime endDate = ParseUtil.getLocalDateTimeFromString(arguments[1]).plusDays(1);
            if (startDate.isAfter(endDate)) {
                message.append("Первая дата позднее второй!");
            } else {
                getPurchases(startDate, endDate)
                        .forEach(item -> message.append(item.toString()));
            }
        } catch (DateTimeException e) {
            message.append("Неверный формат дат!(требуется dd-MM-yyyy)");
        }
    }

    public File getStatistic(String[] arguments, StringBuilder message) {

        LocalDateTime startDate = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime endDate = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).plusDays(1);

        if (arguments.length == 2) {
            try {
                startDate = ParseUtil.getLocalDateTimeFromString(arguments[0]);
                endDate = ParseUtil.getLocalDateTimeFromString(arguments[1]).plusDays(1);
                if (startDate.isAfter(endDate)) {
                    message.append("Первая дата позднее второй!");
                    return null;
                }
            } catch (DateTimeException e) {
                message.append("Неверный формат дат! Требуется dd-MM-yyyy");
                return null;
            }
        }

        BigDecimal total = totalSpend(startDate, endDate);
        List<StatisticDto> statisticData = getStatisticData(startDate, endDate);
        message.append(getStaticticMsg(statisticData, total));
        message.append("Всего потрачено: ").append(total == null ? 0 : total).append(" руб.");
        if (total != null) {
            return DataToImageService.convert(statisticData, startDate.toLocalDate(), endDate.toLocalDate());
        }

        return null;
    }


    private void createAndSaveProduct(String[] arguments, User user, int price,  StringBuilder message) throws NumberFormatException {
        Product product = ArgsToEntityConverter.toProduct(arguments, price, user.getFirstName());
        productRepository.save(product);
        message.append(String.format("Трата %s по цене %s успешно добавлена.", arguments[0], arguments[1]));
    }

    private void deleteProduct(String[] arguments, int price,  StringBuilder message) throws NumberFormatException {
        deleteByCategoryAndPrice(arguments[0], price);
        message.append(String.format("Трата %s по цене %s руб. успешно удалена.", arguments[0], arguments[1]));
    }

    private List<Product> getPurchases(LocalDateTime start, LocalDateTime end){
        return productRepository.getAllByDataBetweenAndDeletedFalse(start, end);
    }

    private Product deleteByCategoryAndPrice(String category, int price){
        Product product = productRepository.getByCategoryAndPrice(category, price);
        product.setDeleted(true);
        return productRepository.save(product);
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

    public BigDecimal totalSpend(LocalDateTime start, LocalDateTime end){
        return productRepository.getSum(start, end);
    }

    /**
     * в отсутствие активности  в течение 30 минут heroku усыпляет приложение.
     */
    @Scheduled(fixedDelay = 1500000)
    public void scheduledTask() {
        LOG.info("Bot scheduled action every 25 minutes");
    }

}
