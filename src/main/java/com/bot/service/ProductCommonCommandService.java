package com.bot.service;

import com.bot.model.Action;
import com.bot.model.Category;
import com.bot.model.entities.Product;
import com.bot.repositories.ProductRepository;
import com.bot.service.converter.ArgsToEntityConverter;;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Arrays;

@Slf4j
@Service
public class ProductCommonCommandService {

    @Autowired
    private ProductRepository productRepository;


    public boolean commonAction(String[] arguments, User user, StringBuilder message, Action action) {
        try {
            if (arguments.length < 2) {
                throw new NumberFormatException();
            }
            int price = Integer.parseInt(arguments[1]);

            switch (action){
                case ADD:
                    createAndSaveProduct(arguments, user, price, message);
                    break;
                case DELETE:
                    deleteProduct(arguments, price, message);
                    break;
            }
            return true;

        } catch (NumberFormatException e){
            message.append("Неверный формат команды! Используйте команду /help");
            log.error(String.format("Неверный формат ввода от пользователя %s id: %d", user.getFirstName(), user.getId()), e);

        } catch (IllegalArgumentException e){
            message.append("Категория не найдена! Список доступных категорий:\n");
            Arrays.stream(Category.values()).forEach(x -> message.append(x.getName()).append("\n"));
            log.error(String.format("Попытка добавления неизвестной категории от пользователя %s id: %d", user.getFirstName(), user.getId()), e);
        }
        return false;
    }

    private void createAndSaveProduct(String[] arguments, User user, int price,  StringBuilder message) throws IllegalArgumentException {
        Product product = ArgsToEntityConverter.toProduct(arguments, price, user.getFirstName());
        productRepository.save(product);
        message.append(String.format("Трата %s по цене %s успешно добавлена.", arguments[0], arguments[1]));
    }

    private void deleteProduct(String[] arguments, int price,  StringBuilder message) throws IllegalArgumentException {
        if(!Category.containsCategory(arguments[0])){
            throw new IllegalArgumentException();
        }
        deleteByCategoryAndPrice(Category.getCategoryByName(arguments[0]), price);
        message.append(String.format("Трата %s по цене %s руб. успешно удалена.", arguments[0], arguments[1]));
    }

    private Product deleteByCategoryAndPrice(Category category, int price){
        Product product = productRepository.getByCategoryAndPrice(category, price);
        product.setDeleted(true);
        return productRepository.save(product);
    }

    /**
     * в отсутствие активности в течение 30 минут heroku усыпляет приложение.
     */
    @Scheduled(fixedDelay = 1500000)
    public void scheduledTask() {
        log.info("Bot scheduled action every 25 minutes");
    }

}
