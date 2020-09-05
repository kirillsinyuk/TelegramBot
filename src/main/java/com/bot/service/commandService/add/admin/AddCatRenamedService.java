package com.bot.service.commandService.add.admin;

import com.bot.model.entities.Band;
import com.bot.model.entities.Category;
import com.bot.model.enumeration.CommonAction;
import com.bot.model.enumeration.DataAction;
import com.bot.service.entity.BotUserService;
import com.bot.service.entity.CategoryService;
import com.bot.service.keyboard.DataKeyboardService;
import com.bot.service.util.ValidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class AddCatRenamedService {

    @Autowired
    private DataKeyboardService dataKeyboardService;
    @Autowired
    private BotUserService botUserService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ValidService validService;

    //как понять, что пользователь ввёл название текущей категории? видимо, только через кэш в бд. Иначе можно потерять данные
    //но пока пусть будет временный вариант.
    //TODO переделать на хранение в БД
    private Map<Long, Category> categoryCash = new HashMap<>();

    @Transactional
    public InlineKeyboardMarkup catRenameCommand(String[] arguments, User user, StringBuilder message){
        return categoryRenameHandler(arguments, botUserService.getBandByUser(user), message);
    }

    private InlineKeyboardMarkup categoryRenameHandler(String[] args, Band band, StringBuilder message){
        args = args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length);

        if(args.length == 0) {
            message.append("Выберите категорию:");
            return dataKeyboardService.categoriesKeyboard(CommonAction.ADD, DataAction.CAT_RENAME, band);
        } else {
            return renameCategory(args, band, message);
        }
    }

    @Transactional
    InlineKeyboardMarkup renameCategory(String[] args, Band band, StringBuilder message) {
        String categoryName = validService.joinArgs(args);
        if (categoryCash.get(band.getId()) == null) {
            checkAndPutInCash(band, message, categoryName);
        } else {
            String newName = getNewCategoryName(categoryCash.get(band.getId()).getName(), categoryName);
            checkAndRename(band, message, newName);
            return dataKeyboardService.basicKeyboardMarkup();
        }
        return new InlineKeyboardMarkup();
    }

    @Transactional
    void checkAndPutInCash(Band band, StringBuilder message, String categoryName) {
        if (validOldCategory(categoryName, band.getCategories(), message)) {
            categoryCash.put(band.getId(), categoryService.getCategoryByName(categoryName, band.getCategories()));
            message.append("Введите новое название категории:");
        }
    }

    private String getNewCategoryName(String oldCategoryName, String categoryName) {
        return categoryName.replaceFirst(oldCategoryName, "").trim();
    }

    @Transactional
    void checkAndRename(Band band, StringBuilder message, String categoryName) {
        if(validNewCategory(categoryName, band.getCategories(), message)) {
            renameCategory(categoryName, categoryCash.get(band.getId()), message);
            categoryCash.remove(band.getId());
        }
    }

    private boolean validOldCategory(String category, Set<Category> categories, StringBuilder message){
        if(!validService.validCatLength(category)){
            message.append("Слишком длинное название категории.(не более 25 символов)");
            return false;
        } else if(!validService.catAlreadyExist(category, categories)){
            message.append("Нет категории с таким именем.");
            return false;
        }
        return true;
    }

    private boolean validNewCategory(String category, Set<Category> categories, StringBuilder message){
        if(!validService.validCatLength(category)){
            message.append("Слишком длинное название категории.(не более 25 символов)");
            return false;
        } else if(validService.catAlreadyExist(category, categories)){
            message.append("Уже существует категория с аналогичным названием.");
            return false;
        }
        return true;
    }

    @Transactional
    void renameCategory(String newName, Category category, StringBuilder message) {
        categoryService.changeName(newName, category);
        message.append(String.format("Категория \"%s\" успешно добавлена.", category.getName()));
    }

}
