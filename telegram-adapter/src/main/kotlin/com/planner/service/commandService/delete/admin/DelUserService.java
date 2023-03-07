package com.planner.service.commandService.delete.admin;

import com.planner.model.entities.*;
import com.planner.repository.*;
import com.planner.service.entity.*;
import com.planner.service.keyboard.*;
import com.planner.service.util.*;
import java.util.*;
import java.util.stream.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;

@Service
@Transactional
public class DelUserService {

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
    @Autowired
    private ValidService validService;

    public InlineKeyboardMarkup delUserCommand(String[] args, User user, StringBuilder message){
        return delUserHandler(args, userService.getUserByUserId(user.getId()), message);
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
            delUserFromBand(userService.getUserByUserId(user_id));
            message.append("Пользователь успешно удалён.");
        }
        return dataKeyboardService.basicKeyboardMarkup();
    }

    boolean validData(String[] args, UserGroup band, StringBuilder message){
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
        UserGroup oldBand = user.getBand();
        UserGroup newBand = bandService.createNewGroup(user);
        categoryService.createCategories(categoryService.getCategoriesByBand(oldBand), newBand);
        // для всех трат изменить в тратах category_id на category_id категорий группы.
        changeSpendingsCategories(categoryService.getCategoriesByBand(oldBand), categoryService.getCategoriesByBand(newBand), user);
        //изменить группу пользователя
        userService.changeUserGroup(user, newBand);
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
