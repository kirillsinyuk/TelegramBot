package com.bot.service.commandService.delete;

import com.bot.model.entities.BotUser;
import com.bot.model.entities.Category;
import com.bot.model.entities.Product;
import com.bot.model.menu.CommonAction;
import com.bot.model.menu.DataAction;
import com.bot.repositories.ProductRepository;
import com.bot.service.entity.BotUserService;
import com.bot.service.entity.CategoryService;
import com.bot.service.entity.ProductService;
import com.bot.service.keyboard.DataKeyboardService;
import com.bot.service.util.ValidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Arrays;

@Service
public class DeleteDataService {

    @Autowired
    private ProductService productService;
    @Autowired
    private DataKeyboardService dataKeyboardService;
    @Autowired
    private BotUserService botUserService;
    @Autowired
    private ValidService validService;
    @Autowired
    private CategoryService categoryService;

    @Transactional
    public InlineKeyboardMarkup deleteCommand(String[] args, User user, StringBuilder message){
        return delCommandHandler(args, botUserService.getBotUserByUserId(user.getId()), message);
    }

    private InlineKeyboardMarkup delCommandHandler(String[] args, BotUser user, StringBuilder message) {
        args = args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length);

        switch (args.length){
            case 0:
                message.append("Выберите категорию:");
                return dataKeyboardService.categoriesKeyboard(CommonAction.DELETE, DataAction.DATA, user.getBand());
            case 1:
                message.append("Введите цену и коммертарий(опционально)");
                return new InlineKeyboardMarkup();
            default: {
                return delData(args, user, message);
            }
        }
    }

    private InlineKeyboardMarkup delData(String[] args, BotUser user, StringBuilder message) {
        if(validService.validData(args, user.getBand().getCategories(), message)){
            int price = Integer.parseInt(args[1]);
            Category category = categoryService.getCategoryByName(args[0], user.getBand().getCategories());
            productService.deleteByCategoryAndPrice(category, price, message);
        }
        return dataKeyboardService.basicKeyboardMarkup();
    }
}
