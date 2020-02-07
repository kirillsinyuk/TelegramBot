package com.bot.service;

import com.bot.model.Category;
import com.bot.model.dto.StatisticDto;
import com.bot.model.entities.Product;
import com.bot.repositories.ProductRepository;
import com.bot.service.converter.ArgsToEntityConverter;
import com.bot.service.util.CalculateUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Logger LOG;


    public boolean createAndSaveProduct(String[] arguments, User user, StringBuilder message) {
        try {
            if (arguments.length < 2) {
                throw new NumberFormatException();
            }
            int price = Integer.parseInt(arguments[1]);

            Product product = ArgsToEntityConverter.toProduct(arguments, price, user.getFirstName());
            productRepository.save(product);
            message.append(String.format("Трата %s по цене %s успешно добавлена.", arguments[0], arguments[1]));
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

    public List<Product> getPurchases(LocalDateTime start, LocalDateTime end){
        return productRepository.getAllByDataBetweenAndDeletedFalse(start, end);
    }

    public Product deleteByCategoryAndPrice(String category, int price){
        Product product = productRepository.getByCategoryAndPrice(category, price);
        product.setDeleted(true);
        return productRepository.save(product);
    }

    public List<StatisticDto> getStatistic(LocalDateTime start, LocalDateTime end){
        return productRepository.getStatistic(start, end)
                .stream()
                .map(ArgsToEntityConverter::toStatisticsDto)
                .collect(Collectors.toList());
    }

    public String getStaticticMsg(List<StatisticDto> data, BigDecimal total) {
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

}
