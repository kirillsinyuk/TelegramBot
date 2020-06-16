package com.bot.service.commandService.add.admin;

import com.bot.model.entities.Band;
import com.bot.model.entities.BotUser;
import com.bot.model.entities.Category;
import com.bot.repositories.ProductRepository;
import com.bot.service.entity.BandService;
import com.bot.service.entity.BotUserService;
import com.bot.service.entity.CategoryService;
import com.bot.service.keyboard.DataKeyboardService;
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
public class AddUserService {

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

    public InlineKeyboardMarkup addUserCommand(String[] args, User user, StringBuilder message){
        return addUserHandler(args, user, message);
    }

    private InlineKeyboardMarkup addUserHandler(String[] args, User user, StringBuilder message) {
        args = args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length);

        if (args.length == 0) {
            message.append("Отправьте контакт:");
            return new InlineKeyboardMarkup();
        }
        addUserToBand(args, user, message);
        return dataKeyboardService.basicKeyboardMarkup();
    }

    @Transactional
    void addUserToBand(String[] args, User user, StringBuilder message) {
        BotUser botUser = botUserService.getBotUserByUserId(user.getId());
        if(validData(args, botUser.getBand(), message)){
            int user_id = Integer.parseInt(args[0]);
            addUserToBand(botUser.getBand(), botUserService.getBotUserByUserId(user_id));
            message.append("Пользователь успешно добавлен.");
        }
    }
//
    @Transactional
    boolean validData(String[] args, Band band, StringBuilder message){
        if(!isBotUserExist(args[0])){
            message.append("Данный пользователь не является пользователем бота.");
            return false;
        }
        if(inThisBandAlready(Integer.parseInt(args[0]), botUserService.getUsersByBand(band))){
            message.append("Данный пользователь уже в группе.");
            return false;
        }
        return true;
    }

    private boolean isBotUserExist(String arg) {
        return botUserService.isUserExist(Integer.parseInt(arg));
    }

    @Transactional
    boolean inThisBandAlready(int user_id, Set<BotUser> users){
        return users.stream().anyMatch(user -> user.getUserId().equals(user_id));
    }

    @Transactional
    void addUserToBand(Band band, BotUser user) throws IllegalArgumentException {
        Band oldBand = user.getBand();
        Set<Category> userCat = categoryService.getCategoriesByBand(user.getBand());
        Set<Category> bandCat = categoryService.getCategoriesByBand(band);
        //найти разницу в категориях. добавить их в группу.
        categoryService.addCategoriesToGroup(categoryService.getCategoryDiff(bandCat, userCat), band);
        // для всех трат изменить в тратах category_id на category_id категорий группы.
        changeSpendingsCategories(userCat, bandCat, user);
        //изменить группу пользователя (нужно ли его спросить об этом? думаю, да, но потом)
        botUserService.changeUserBand(user, band);
        bandService.isNotSingleAnymore(band);
        //удалить группу приглашенного пользователя со всеми категориями. (не удаляя траты)
        deleteBandIfSingle(oldBand);

    }

    @Transactional
    void deleteBandIfSingle(Band band) {
        if(band.isSingle()) {
            bandService.deleteBandWithCategories(band);
        } else {
            setNewAdmin(band);
        }
    }

    private void setNewAdmin(Band band) {
        band.setAdminId(
                botUserService.getUsersByBand(band)
                        .stream()
                        .findFirst()
                        .get()
                        .getUserId()
        );
        bandService.saveBand(band);
    }

    @Transactional
    void changeSpendingsCategories(Set<Category> from, Set<Category> to, BotUser botUser) {
        changeCategories(getNewCategoryIdMap(from, to), botUser);
    }

    @Transactional
    void changeCategories(Map<Category, Category> categoryIdMap, BotUser user) {
        categoryIdMap.forEach(
                (key, value) -> {
                    productRepository.getAllByCategoryAndUser(key, user)
                            .stream()
                            .parallel()
                            .forEach(product -> {
                                        product.setCategory(value);
                                        productRepository.save(product);
                                    }
                            );
                    //TODO ГЛУПОСТЬ. не null, а разница
                    key.setProducts(null);
                    categoryService.saveCategory(key);
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
