package com.bot.service;

import com.bot.model.entities.Product;
import com.bot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BotService botService;

    @Transactional
    public Product createAndSaveProduct(String[] arguments, String username){
           Product product = toProduct(arguments, username);
           return productRepository.save(product);
    }

    @Transactional
    public List<Product> getPurchases(LocalDateTime start, LocalDateTime end){
        return productRepository.getAllByDataBetween(start, end);
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
}
