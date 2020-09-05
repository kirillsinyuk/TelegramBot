package com.bot.model.repository;

import com.bot.model.entities.Category;
import com.bot.model.entities.Band;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategogyRepository extends CrudRepository<Category, Long> {

    Set<Category> findByBand(Band band);
}
