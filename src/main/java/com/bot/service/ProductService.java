package com.bot.service;

import com.bot.model.entities.Product;
import com.bot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createAndSaveProduct(String[] arguments){
           Product product = toProduct(arguments);
           return productRepository.save(product);
    }

    private Product toProduct(String[] arguments){
        Product product = new Product();
        product.setCategory(arguments[0]);
        product.setPrice(Integer.parseInt(arguments[1]));
        product.setData(LocalDateTime.now());
        if (arguments.length == 3) {
            product.setDescription(arguments[2]);
        }
        return product;
    }
}
