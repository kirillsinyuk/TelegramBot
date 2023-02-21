package com.planner.service.commandService.add.admin;

import com.planner.model.entities.*;
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
public class AddCatService {

    @Autowired
    private DataKeyboardService dataKeyboardService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ValidService validService;

    @Transactional
    public InlineKeyboardMarkup addCatCommand(String[] arguments, User user, StringBuilder message){
        return addCategoryHandler(arguments, userService.getGroupByUser(user), message);
    }

    private InlineKeyboardMarkup addCategoryHandler(String[] args, UserGroup band, StringBuilder message){
        args = args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length);

        if (args.length == 0) {
            message.append("Введите название новой категории:");
            return new InlineKeyboardMarkup();
        }
        return addCategory(validService.joinArgs(args), band, message);
    }

    @Transactional
    InlineKeyboardMarkup addCategory(String category, UserGroup band, StringBuilder message){
        if(validService.validCategoryAndNotExist(category, band.getCategories(), message)){
            createAndSaveCategory(category, band);
            message.append(String.format("Категория \"%s\" успешно добавлена.", category));
            return dataKeyboardService.basicKeyboardMarkup();
        }
        return new InlineKeyboardMarkup();
    }

    @Transactional
    void createAndSaveCategory(String categoryName, UserGroup band){
        categoryService.createCategory(categoryName, band);
    }

}
