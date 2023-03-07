package com.planner.service.entity;

import com.planner.model.enumeration.*;
import com.planner.repository.*;
import java.util.*;
import java.util.stream.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory(String name, UserGroup band){
        Category category = new Category();

        category.setName(name);
        category.setBand(band);

        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Category category){
        category.setProducts(null);
        categoryRepository.delete(category);
    }

    @Transactional
    public void saveCategory(Category category){
        categoryRepository.save(category);
    }

    public void createCommonCategories(UserGroup band) {
        Arrays.stream(CommonCategories.values())
                .forEach(cat -> createCategory(cat.getName(), band));
    }

    public void createCategories(Set<Category> categories, UserGroup band) {
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
        return categoryRepository.save(category);
    }

    @Transactional
    public void addCategoriesToGroup(Set<Category> categories, UserGroup band){
        Set<Category> category = categories.stream()
                .peek(cat -> cat.setBand(band))
                .collect(Collectors.toSet());
        categoryRepository.saveAll(category);
    }

    public Set<Category> getCategoriesByBand(UserGroup band){
        return categoryRepository.findByGroup(band);
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
        return categoryRepository.findById(id).orElse(null);
    }

    public void deleteAll(Set<Category> categories){
        categoryRepository.deleteAll(categories);
    }
}
