package com.bot.service.commandService.delete.admin;

import com.bot.model.entities.Band;
import com.bot.model.entities.Category;
import com.bot.model.menu.CommonAction;
import com.bot.model.menu.DataAction;
import com.bot.repositories.ProductRepository;
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
public class DelCatService {

    @Autowired
    private DataKeyboardService dataKeyboardService;
    @Autowired
    private BotUserService botUserService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ValidService validService;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public InlineKeyboardMarkup delCatCommand(String[] arguments, User user, StringBuilder message){
        return delCategoryHandler(arguments, botUserService.getBandByUser(user), message);
    }

    private InlineKeyboardMarkup delCategoryHandler(String[] args, Band band, StringBuilder message){
        args = args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length);

        if (args.length == 0) {
            message.append("Выберите категорию.\n(Удаление категории также удалит все траты в этой категории)");
            return dataKeyboardService.categoriesKeyboard(CommonAction.DELETE, DataAction.CATEGORIES, band);
        }
        return delCategory(validService.joinArgs(args), band, message);
    }

    @Transactional
    InlineKeyboardMarkup delCategory(String category, Band band, StringBuilder message){
        if(validService.validCategoryAndExist(category, band.getCategories(), message)){
            deleteCategory(categoryService.getCategoryByName(category, band.getCategories()));
            message.append(String.format("Категория \"%s\" удалена.", category));
        }
        return dataKeyboardService.basicKeyboardMarkup();
    }

    private void deleteCategory(Category category){
        productRepository.deleteAll(productRepository.getAllByCategory(category));
        categoryService.deleteCategory(category);
    }

}
