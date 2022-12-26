package com.bot.model.enumeration;

public enum CommonCategories {

    RENT("Квартплата"),
    PHONES("Интернет и связь"),
    FEED("Еда"),
    ENTERTAINMENT("Развлечения"),
    REALITY("Быт"),
    GIFTS("Подарки"),
    HEALTH("Здоровье"),
    TRANSPORT("Проезд"),
    TRAVEL("Путешествия"),
    OTHER("Прочее"),
    CLOTHES("Одежда");

    private String name;

    public String getName() {
        return name;
    }

    CommonCategories(String name) {
        this.name = name;
    }

}
