package com.bot.service;

import com.bot.model.dto.StatisticDto;
import com.bot.model.entities.Product;
import com.bot.repositories.ProductRepository;
import com.bot.service.util.CalculateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public Product createAndSaveProduct(String[] arguments, String username){
           Product product = toProduct(arguments, username);
           return productRepository.save(product);
    }

    public List<Product> getPurchases(LocalDateTime start, LocalDateTime end){
        return productRepository.getAllByDataBetween(start, end);
    }

    private List<Object[]> getStatistic(LocalDateTime start, LocalDateTime end){
        return productRepository.getStatistic(start, end);
    }

    public String getStaticticMsg(LocalDateTime start, LocalDateTime end, BigDecimal total) {
        StringBuilder str = new StringBuilder();
        getStatistic(start, end)
                .stream()
                .map(this::toStatisticsDto)
                .forEach(price -> str.append(price.toString())
                        .append(" (")
                        .append(CalculateUtils.getPercent(price.getPrice(), total))
                        .append("%)\n"));
        return str.toString();
    }

    public BigDecimal totalSpend(LocalDateTime start, LocalDateTime end){
        return productRepository.getSum(start, end);
    }

    private Product toProduct(String[] arguments, String username){
        Product product = new Product();
        product.setCategory(arguments[0]);
        product.setPrice(Integer.parseInt(arguments[1]));
        product.setData(LocalDateTime.now());
        product.setSpendedBy(username);
        if (arguments.length == 3) {
            product.setDescription(arguments[2]);
        }
        return product;
    }

    public StatisticDto toStatisticsDto(Object[] obj){
        return new StatisticDto((String)obj[0], (BigDecimal) obj[1]);
    }

}
