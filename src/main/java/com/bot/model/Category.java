package com.bot.model;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static InlineKeyboardMarkup categoriesKeyboard(){
        InlineKeyboardMarkup rkm = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        List<Category> list = Arrays.asList(Category.values());
        int rowSize = 3;
        //grouping buttons by rowSize
        IntStream.range(0, (list.size() + rowSize - 1) / rowSize)
                .mapToObj(i -> list.subList(i * rowSize, Math.min(rowSize * (i + 1), list.size())))
                .map(catList -> catList
                        .stream()
                        .map(c -> new InlineKeyboardButton()
                                        .setText(c.getName())
                                        .setCallbackData("/add " + c.getName())
                        ).collect(Collectors.toList())
                )
                .forEach(keyboard::add);

        rkm.setKeyboard(keyboard);
        return rkm;
    }
}
