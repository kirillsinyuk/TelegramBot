package com.bot.service.entity;

import com.bot.model.dto.DynamicsDataDto;
import com.bot.model.dto.StatisticDataDto;
import com.bot.model.entities.BotUser;
import com.bot.model.entities.Category;
import com.bot.model.entities.Product;
import com.bot.model.repository.ProductRepository;
import com.bot.service.converter.ArgsToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ArgsToEntityConverter argsToEntityConverter;

    @Transactional
    public void deleteByCategoryAndPrice(Category category, int price, StringBuilder msg){
        List<Product> products = productRepository.getByCategoryAndPriceOrderByData(category, price);
        if(products.size() > 0){
            Product product = products.get(0);
            product.setDeleted(true);
            productRepository.save(product);
            msg.append(String.format("Трата %s по цене %s успешно удалена.", category.getName(), price));
        } else {
            msg.append("Нет трат в данной категории по такой цене");
        }
    }

    private Product toProduct(String[] arguments, Category category, BotUser user) {
        int price = Integer.parseInt(arguments[0]);
        String desc = arguments.length > 2 ?
                Arrays.stream(arguments)
                        .skip(2)
                        .collect(Collectors.joining(" "))
                : null;

        return new Product(category, price, LocalDateTime.now(), desc, user);
    }

    public Product createAndSaveProduct(String[] args, Category category, BotUser user){
        return productRepository.save(toProduct(args, category, user));
    }

    public BigDecimal getTotalSpend(LocalDateTime start, LocalDateTime end, BotUser user){
        return productRepository.getSumByUser(start, end, user.getId());
    }

    public BigDecimal getTotalSpendAllTime(BotUser user){
        return productRepository.getSumByUser(user.getId());
    }

    public List<StatisticDataDto> getStatisticDataDto(LocalDateTime start, LocalDateTime end, BotUser user) {
        return productRepository.getStatisticByUser(start, end, user.getId())
                .stream()
                .map(raw -> argsToEntityConverter.toStatisticsDto(raw))
                .collect(Collectors.toList());
    }

    public List<DynamicsDataDto> getDynamicsDataDto(BotUser user) {
        return productRepository.getDynamicsByUser(user.getId()).stream()
                .map(raw -> argsToEntityConverter.toDynamicsDto(raw, user.getUsername()))
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalSpend(LocalDateTime start, LocalDateTime end){
        return productRepository.getSum(start, end);
    }

    public List<StatisticDataDto> getStatisticDataDto(LocalDateTime start, LocalDateTime end) {
        return productRepository.getStatistic(start, end)
                .stream()
                .map(raw -> argsToEntityConverter.toStatisticsDto(raw))
                .collect(Collectors.toList());
    }

    public Set<Product> getAllSpendingsByUser(BotUser user){
        return productRepository.getAllByUser(user);
    }

    public void deleteAllSpendings(BotUser user){
        productRepository.deleteAll(getAllSpendingsByUser(user));
    }
}
