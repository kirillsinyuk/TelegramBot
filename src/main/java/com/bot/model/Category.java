package com.bot.model;

import java.util.Arrays;

public enum Category {

    /*
    Категории:
    Квартплата
    Связь
    Еда
    Карманные
    Развлечения
    Быт
    Подарки
    Терапия
    Здоровье
    Прочее
    Проезд
    Женское
    Лишнее
    Путушествия
    Обеды
    Одежда
    Обучение
 */
    RENT("квартплата"),
    PHONES("связь"),
    FEED("еда"),
    POCKET_MONEY("карманные"),
    ENTERTAINMENT("развлечения"),
    REALITY("быт"),
    GIFTS("подарки"),
    THERAPY("терапия"),
    HEALTH("здоровье"),
    TRANSPORT("проезд"),
    WOMAN_STUFF("женское"),
    REDUNDANT("лишнее"),
    TRAVEL("путешествия"),
    LUNCH("обеды"),
    OTHER("прочее"),
    CLOTHES("одежда"),
    EDUCATION("обучение");

    private String name;

    public String getName() {
        return name;
    }

    Category(String name) {
        this.name = name;
    }

    public static boolean containsCategory(String description){
        return Arrays.stream(Category.values()).anyMatch(category -> category.name.equals(description.toLowerCase()));
    }

    public static Category getCategoryByName(String description){
        return Arrays.stream(Category.values()).filter(category -> category.name.equals(description.toLowerCase())).findFirst().orElse(null);
    }

    public static String getNameByCategory(String category){
        return Arrays.stream(Category.values()).filter(item -> item.toString().equals(category)).map(Category::getName).findFirst().orElse(null);
    }

    public static String getNameByCategory(Category category){
        return Arrays.stream(Category.values()).filter(item -> item.equals(category)).map(Category::getName).findFirst().orElse(null);
    }
}
