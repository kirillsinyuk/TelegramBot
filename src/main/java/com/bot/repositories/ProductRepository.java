package com.bot.repositories;

import com.bot.model.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> getAllByDataBetween(LocalDateTime startDate, LocalDateTime endDate);

    void deleteAllByCategoryAndPrice(String category, int price);

    @Query(value = "SELECT p.category, SUM(p.price) FROM product p WHERE add_data BETWEEN startDate AND endDate GROUP BY p.category", nativeQuery = true)
    List<Object[]> getStatistic(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT SUM(p.price) FROM product p WHERE add_data BETWEEN startDate AND endDate", nativeQuery = true)
    int getSum(LocalDateTime startDate, LocalDateTime endDate);
}
