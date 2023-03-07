package com.planner.service.commandService.add;

import com.planner.model.enumeration.*;
import com.planner.service.entity.*;
import com.planner.service.keyboard.*;
import com.planner.service.util.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;

@Service
public class AddDataService {

    @Autowired
    private DataKeyboardService dataKeyboardService;
    @Autowired
    private UserService userService;
    @Autowired
    private ValidService validService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    //но пока пусть будет временный вариант.
    //TODO переделать на хранение в БД
    private Map<Long, Category> categoryCash = new HashMap<>();

    @Transactional
    public InlineKeyboardMarkup addDataCommand(String[] args, User user, StringBuilder message){
        return addDataHandler(args, userService.getUserByUserId(user.getId()), message);
    }

    private InlineKeyboardMarkup addDataHandler(String[] args, BotUser user, StringBuilder message) {
        args = args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length);

        if (args.length == 0) {
            message.append("Выберите категорию:");
            return dataKeyboardService.categoriesKeyboard(CommonAction.ADD, DataAction.DATA, user.getBand());
        }
        return addData(args, user, message);
    }

    @Transactional
    InlineKeyboardMarkup addData(String[] args, BotUser user, StringBuilder message) {
        String categoryName = validService.joinArgs(args);
        if (categoryCash.get(user.getId()) == null) {
            return checkAndPutInCash(user, message, categoryName);
        } else {
            args = getArgsWithoutCat(categoryCash.get(user.getId()).getName(), args);
            return addspendingIfValid(args, categoryCash.get(user.getId()), user, message);
        }
    }

    private String[] getArgsWithoutCat(String categoryName, String[] args) {
        return validService.joinArgs(args).replaceFirst(categoryName, "").trim().split(" ");
    }

    @Transactional
    InlineKeyboardMarkup checkAndPutInCash(BotUser user, StringBuilder message, String categoryName) {
        if (validOldCategory(categoryName, user.getBand().getCategories(), message)) {
            categoryCash.put(user.getId(), categoryService.getCategoryByName(categoryName, user.getBand().getCategories()));
            message.append("Введите цену и коммертарий(опционально)");
            return new InlineKeyboardMarkup();
        }
        return dataKeyboardService.basicKeyboardMarkup();
    }

    private boolean validOldCategory(String category, Set<Category> categories, StringBuilder message){
        if(!validService.validCatLength(category)){
            message.append("Слишком длинное название категории.(не более 25 символов)");
            return false;
        } else if(!validService.catAlreadyExist(category, categories)){
            message.append("В этой группе нет категории с таким именем.");
            return false;
        }
        return true;
    }

    @Transactional
    InlineKeyboardMarkup addspendingIfValid(String[] args, Category category, BotUser user, StringBuilder message) {
        if(validService.validData(args, category, user.getBand().getCategories(), message)){
            createAndSaveProduct(args, category, user);
            message.append(String.format("Трата %s по цене %s успешно добавлена.", category.getName(), args[0]));
        }
        categoryCash.remove(user.getId());
        return dataKeyboardService.basicKeyboardMarkup();
    }

    private void createAndSaveProduct(String[] args, Category category, BotUser user) throws IllegalArgumentException {
        productService.createAndSaveProduct(args, category, user);
    }

}
