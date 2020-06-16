package com.bot.repositories;

import com.bot.model.entities.BotUser;
import com.bot.model.entities.Category;
import com.bot.model.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> getAllByDataBetweenAndDeletedFalse(LocalDateTime startDate, LocalDateTime endDate);

    List<Product> getByCategoryAndPriceOrderByData(Category category, int price);

    @Query(value = "SELECT p.category_id, SUM(p.price) FROM product p WHERE add_data BETWEEN ?1 AND ?2 AND p.deleted=false GROUP BY p.category_id", nativeQuery = true)
    List<Object[]> getStatistic(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT p.category_id, SUM(p.price) FROM product p WHERE add_data BETWEEN ?1 AND ?2 AND p.deleted=false AND user_id=?3 GROUP BY p.category_id", nativeQuery = true)
    List<Object[]> getStatisticByUser(LocalDateTime startDate, LocalDateTime endDate, Long user_id);

    @Query(value = "SELECT SUM(p.price) FROM product p WHERE add_data BETWEEN ?1 AND ?2 AND p.deleted=false", nativeQuery = true)
    BigDecimal getSum(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT SUM(p.price) FROM product p WHERE add_data BETWEEN ?1 AND ?2 AND p.deleted=false AND user_id=?3", nativeQuery = true)
    BigDecimal getSumByUser(LocalDateTime startDate, LocalDateTime endDate, Long user_id);

    Set<Product> getAllByCategoryAndUser(Category category, BotUser user);

    Set<Product> getAllByCategory(Category category);
}
