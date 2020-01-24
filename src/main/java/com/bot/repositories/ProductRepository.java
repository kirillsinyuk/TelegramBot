package com.bot.repositories;

import com.bot.model.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> getAllByDataBetween(LocalDateTime startDate, LocalDateTime endDate);
}
