package com.planner.service.commandService.delete.admin;

import com.planner.model.entities.*;
import com.planner.model.enumeration.*;
import com.planner.repository.*;
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
public class DelCatService {

    @Autowired
    private DataKeyboardService dataKeyboardService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ValidService validService;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public InlineKeyboardMarkup delCatCommand(String[] arguments, User user, StringBuilder message){
        return delCategoryHandler(arguments, userService.getGroupByUser(user), message);
    }

    private InlineKeyboardMarkup delCategoryHandler(String[] args, UserGroup band, StringBuilder message){
        args = args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length);

        if (args.length == 0) {
            message.append("Выберите категорию.\n(Удаление категории также удалит все траты в этой категории)");
            return dataKeyboardService.categoriesKeyboard(CommonAction.DELETE, DataAction.CATEGORIES, band);
        }
        return delCategory(validService.joinArgs(args), band, message);
    }

    @Transactional
    InlineKeyboardMarkup delCategory(String category, UserGroup band, StringBuilder message){
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
