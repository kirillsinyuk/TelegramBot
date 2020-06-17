package com.bot.service.entity;

import com.bot.model.entities.Band;
import com.bot.model.entities.Category;
import com.bot.model.menu.CommonCategories;
import com.bot.repositories.CategogyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategogyRepository categogyRepository;

    @Transactional
    public Category createCategory(String name, Band band){
        Category category = new Category();

        category.setName(name);
        category.setBand(band);

        return categogyRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Category category){
        category.setProducts(null);
        categogyRepository.delete(category);
    }

    @Transactional
    public void saveCategory(Category category){
        categogyRepository.save(category);
    }

    public void createCommonCategories(Band band) {
        Arrays.stream(CommonCategories.values())
                .forEach(cat -> createCategory(cat.getName(), band));
    }

    public void createCategories(Set<Category> categories, Band band) {
        categories.forEach(cat -> createCategory(cat.getName(), band));
    }

    public Category getCategoryByName(String name, Set<Category> categories){
        return categories.stream()
                .filter(category -> category.getName().toLowerCase().equals(name.toLowerCase()))
                .findFirst()
                .get();
    }

    public Category changeName(String name, Category category){
        category.setName(name);
        return categogyRepository.save(category);
    }

    @Transactional
    public void addCategoriesToGroup(Set<Category> categories, Band band){
        Set<Category> category = categories.stream()
                .peek(cat -> cat.setBand(band))
                .collect(Collectors.toSet());
        categogyRepository.saveAll(category);
    }

    public Set<Category> getCategoriesByBand(Band band){
        return categogyRepository.findByBand(band);
    }

    public Set<Category> getCategoryDiff(Set<Category> bandCat, Set<Category> userCat){
        Set<String> userCategoryNames = userCat.stream()
                .map(Category::getName)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
        return bandCat.stream()
                .filter(category -> !userCategoryNames.contains(category.getName().toLowerCase()))
                .collect(Collectors.toSet());
    }

    public Category getCategoryById(Long id){
        return categogyRepository.findById(id).orElse(null);
    }
}
