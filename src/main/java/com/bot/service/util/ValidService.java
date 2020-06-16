package com.bot.service.util;

import com.bot.model.entities.BotUser;
import com.bot.model.entities.Category;
import com.bot.model.entities.Product;
import com.bot.service.entity.BotUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Set;

@Service
public class ValidService {

    @Autowired
    private BotUserService botUserService;

    public boolean validPrice(String arg) {
        try{
            return Integer.parseInt(arg) > 0;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public boolean validDataComment(String[] args) {
        String comment = joinArgs(Arrays.copyOfRange(args, 2, args.length));
        if(args.length >= 2) {
            return validCommentLength(comment);
        }

        return true;
    }

    public boolean validCategoryAndNotExist(String category, Set<Category> categories, StringBuilder message){
        if(!validCatLength(category)){
            message.append("Слишком длинное название категории.(не более 25 символов)");
            return false;
        } else if(catAlreadyExist(category, categories)){
            message.append("Существует категория с аналогичным названием.");
            return false;
        }
        return true;
    }

    public boolean validCategoryAndExist(String category, Set<Category> categories, StringBuilder message){
        if(!validCatLength(category)){
            message.append("Слишком длинное название категории.(не более 25 символов)");
            return false;
        } else if(!catAlreadyExist(category, categories)){
            message.append("Не существует категории с аналогичным названием.");
            return false;
        }
        return true;
    }

    public boolean validData(String[] args, Set<Category> categories, StringBuilder message){
        if(!validPrice(args[1])){
            message.append("Цена введена неверно.(целое число больше 0 и меньше 2,147,483,647) (;");
            return false;
        } else if(!validDataComment(args)){
            message.append("Длина комментария превышает допустимую.(64 символа)");
            return false;
        } else if(!catAlreadyExist(args[0], categories)){
            message.append("Категории с таким названием не существует для этой группы.");
            return false;
        }
        return true;
    }

    @Transactional
    public boolean catAlreadyExist(String category, Set<Category> categories) {
        return categories.stream()
                .anyMatch(cat -> cat.getName().toLowerCase().equals(category.toLowerCase()));
    }

    public boolean validCatLength(String category) {
        return !StringUtils.isEmpty(category) && category.length() < Category.MAX_NAME_LENGTH;
    }

    public String joinArgs(String[] args){
        return String.join(" ", args).trim();
    }

    public boolean validCommentLength(String comment) {
        return !StringUtils.isEmpty(comment) && comment.length() < Product.MAX_COMMENT_LENGTH;
    }

    public boolean isBotUserExist(String arg) {
        return botUserService.isUserExist(Integer.parseInt(arg));
    }

    public boolean inThisBandAlready(int user_id, Set<BotUser> users){
        return users.stream().anyMatch(user -> user.getUserId().equals(user_id));
    }

}
