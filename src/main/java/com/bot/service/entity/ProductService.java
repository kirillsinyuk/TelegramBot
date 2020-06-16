package com.bot.service.entity;

import com.bot.model.dto.StatisticDataDto;
import com.bot.model.entities.BotUser;
import com.bot.model.entities.Category;
import com.bot.model.entities.Product;
import com.bot.repositories.ProductRepository;
import com.bot.service.converter.ArgsToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private CategoryService categoryService;
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

    private Product toProduct(String[] arguments, BotUser user) {
        Category category = categoryService.getCategoryByName(arguments[0], user.getBand().getCategories());
        int price = Integer.parseInt(arguments[1]);
        String desc = arguments.length >= 3 ?
                Arrays.stream(arguments)
                        .skip(2)
                        .collect(Collectors.joining(" "))
                : null;

        return new Product(category, price, LocalDateTime.now(), desc, user);
    }

    public Product createAndSaveProduct(String[] args, BotUser user){
        return productRepository.save(toProduct(args, user));
    }

    public BigDecimal getTotalSpend(LocalDateTime start, LocalDateTime end, BotUser user){
        return productRepository.getSumByUser(start, end, user.getId());
    }

    public List<StatisticDataDto> getStatisticDataDto(LocalDateTime start, LocalDateTime end, BotUser user) {
        return productRepository.getStatisticByUser(start, end, user.getId())
                .stream()
                .map(raw -> argsToEntityConverter.toStatisticsDto(raw))
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
}
