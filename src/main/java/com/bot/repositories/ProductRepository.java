package com.bot.repositories;

import com.bot.model.Category;
import com.bot.model.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> getAllByDataBetweenAndDeletedFalse(LocalDateTime startDate, LocalDateTime endDate);

    Product getByCategoryAndPrice(Category category, int price);

    @Query(value = "SELECT p.category, SUM(p.price) FROM product p WHERE add_data BETWEEN ?1 AND ?2  AND p.deleted=false  GROUP BY p.category", nativeQuery = true)
    List<Object[]> getStatistic(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT SUM(p.price) FROM product p WHERE add_data BETWEEN ?1 AND ?2 AND p.deleted=false", nativeQuery = true)
    BigDecimal getSum(LocalDateTime startDate, LocalDateTime endDate);
}
