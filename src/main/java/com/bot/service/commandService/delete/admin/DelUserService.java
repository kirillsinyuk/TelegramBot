package com.bot.service.commandService.delete.admin;

import com.bot.model.entities.Band;
import com.bot.model.entities.BotUser;
import com.bot.model.entities.Category;
import com.bot.model.entities.Product;
import com.bot.model.repository.ProductRepository;
import com.bot.service.entity.BandService;
import com.bot.service.entity.BotUserService;
import com.bot.service.entity.CategoryService;
import com.bot.service.keyboard.DataKeyboardService;
import com.bot.service.util.ValidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class DelUserService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DataKeyboardService dataKeyboardService;
    @Autowired
    private BotUserService botUserService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BandService bandService;
    @Autowired
    private ValidService validService;

    public InlineKeyboardMarkup delUserCommand(String[] args, User user, StringBuilder message){
        return delUserHandler(args, botUserService.getBotUserByUserId(user.getId()), message);
    }

    private InlineKeyboardMarkup delUserHandler(String[] args, BotUser user, StringBuilder message) {
        args = args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length);

        if (args.length == 0) {
            message.append("Отправьте контакт:");
            return new InlineKeyboardMarkup();
        }
        return delUser(args, user, message);
    }

    InlineKeyboardMarkup delUser(String[] args, BotUser user, StringBuilder message) {
        if(validData(args, user.getBand(), message)){
            int user_id = Integer.parseInt(args[0]);
            delUserFromBand(botUserService.getBotUserByUserId(user_id));
            message.append("Пользователь успешно удалён.");
        }
        return dataKeyboardService.basicKeyboardMarkup();
    }

    boolean validData(String[] args, Band band, StringBuilder message){
        if(!validService.isBotUserExist(args[0])){
            message.append("Данный пользователь не является пользователем бота.");
            return false;
        }
        if(!validService.inThisBandAlready(Integer.parseInt(args[0]), band.getBandUsers())){
            message.append("Данного пользователя нет в этой группе.");
            return false;
        }
        if(band.getAdminId().equals(Integer.parseInt(args[0]))){
            message.append("Нельзя удалить себя из группы.");
            return false;
        }
        return true;
    }

    void delUserFromBand(BotUser user) throws IllegalArgumentException {
        //создать новую группу
        Band oldBand = user.getBand();
        Band newBand = bandService.createNewBand(user);
        categoryService.createCategories(categoryService.getCategoriesByBand(oldBand), newBand);
        // для всех трат изменить в тратах category_id на category_id категорий группы.
        changeSpendingsCategories(categoryService.getCategoriesByBand(oldBand), categoryService.getCategoriesByBand(newBand), user);
        //изменить группу пользователя
        botUserService.changeUserBand(user, newBand);
        bandService.setSingleIfNeeded(oldBand);
    }

    void changeSpendingsCategories(Set<Category> from, Set<Category> to, BotUser user) {
        changeCategories(getNewCategoryIdMap(from, to), user);
    }

    void changeCategories(Map<Category, Category> categoryIdMap, BotUser user) {
        categoryIdMap.forEach(
                (key, value) -> {
                    Set<Product> prodcts = productRepository.getAllByCategoryAndUser(key, user);
                    prodcts
                        .forEach(product ->
                                    product.setCategory(value));
                    productRepository.saveAll(prodcts);
                });

    }

    private Map<Category, Category> getNewCategoryIdMap(Set<Category> from, Set<Category> to) {
        return from.stream().flatMap(c ->
                to.stream()
                        .filter(c2 -> c.getName().equals(c2.getName()))
                        .map(c2 -> new AbstractMap.SimpleImmutableEntry<>(c, c2)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
