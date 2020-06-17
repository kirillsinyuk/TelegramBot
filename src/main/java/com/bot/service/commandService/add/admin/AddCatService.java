package com.bot.service.commandService.add.admin;

import com.bot.model.entities.Band;
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

@Service
public class AddCatService {

    @Autowired
    private DataKeyboardService dataKeyboardService;
    @Autowired
    private BotUserService botUserService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ValidService validService;

    @Transactional
    public InlineKeyboardMarkup addCatCommand(String[] arguments, User user, StringBuilder message){
        return addCategoryHandler(arguments, botUserService.getBandByUser(user), message);
    }

    private InlineKeyboardMarkup addCategoryHandler(String[] args, Band band, StringBuilder message){
        args = args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length);

        if (args.length == 0) {
            message.append("Введите название новой категории:");
            return new InlineKeyboardMarkup();
        }
        return addCategory(validService.joinArgs(args), band, message);
    }

    @Transactional
    InlineKeyboardMarkup addCategory(String category, Band band, StringBuilder message){
        if(validService.validCategoryAndNotExist(category, band.getCategories(), message)){
            createAndSaveCategory(category, band);
            message.append(String.format("Категория \"%s\" успешно добавлена.", category));
            return dataKeyboardService.basicKeyboardMarkup();
        }
        return new InlineKeyboardMarkup();
    }

    @Transactional
    void createAndSaveCategory(String categoryName, Band band){
        categoryService.createCategory(categoryName, band);
    }

}
