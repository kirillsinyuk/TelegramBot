package com.planner.service.commandService.add.admin;

import com.planner.model.entities.*;
import com.planner.repository.*;
import com.planner.service.entity.*;
import com.planner.service.keyboard.*;
import java.util.*;
import java.util.stream.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;

@Service
public class AddUserService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DataKeyboardService dataKeyboardService;
    @Autowired
    private UserService userService;
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
        BotUser botUser = userService.getUserByUserId(user.getId());
        if(validData(args, botUser.getBand(), message)){
            int user_id = Integer.parseInt(args[0]);
            addUserToBand(botUser.getBand(), userService.getUserByUserId(user_id));
            message.append("Пользователь успешно добавлен.");
        }
    }
//
    @Transactional
    boolean validData(String[] args, UserGroup band, StringBuilder message){
        if(!isBotUserExist(args[0])){
            message.append("Данный пользователь не является пользователем бота.");
            return false;
        }
        if(inThisBandAlready(Integer.parseInt(args[0]), userService.getUsersByGroup(band))){
            message.append("Данный пользователь уже в группе.");
            return false;
        }
        return true;
    }

    private boolean isBotUserExist(String arg) {
        return userService.isUserExist(Integer.parseInt(arg));
    }

    @Transactional
    boolean inThisBandAlready(int user_id, Set<BotUser> users){
        return users.stream().anyMatch(user -> user.getUserId().equals(user_id));
    }

    @Transactional
    void addUserToBand(UserGroup band, BotUser user) throws IllegalArgumentException {
        UserGroup oldBand = user.getBand();
        Set<Category> userCat = categoryService.getCategoriesByBand(user.getBand());
        Set<Category> bandCat = categoryService.getCategoriesByBand(band);
        //найти разницу в категориях. добавить их в группу.
        categoryService.addCategoriesToGroup(categoryService.getCategoryDiff(bandCat, userCat), band);
        // для всех трат изменить в тратах category_id на category_id категорий группы.
        changeSpendingsCategories(userCat, bandCat, user);
        //изменить группу пользователя (нужно ли его спросить об этом? думаю, да, но потом)
        userService.changeUserGroup(user, band);
        bandService.isNotSingleAnymore(band);
        //удалить группу приглашенного пользователя со всеми категориями. (не удаляя траты)
        deleteBandIfSingle(oldBand);

    }

    @Transactional
    void deleteBandIfSingle(UserGroup band) {
        if(band.isSingle()) {
            bandService.deleteBandWithCategories(band);
        } else {
            setNewAdmin(band);
        }
    }

    private void setNewAdmin(UserGroup band) {
        band.setAdminId(
                userService.getUsersByGroup(band)
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
