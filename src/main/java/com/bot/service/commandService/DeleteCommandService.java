package com.bot.service.commandService;

import com.bot.model.Category;
import com.bot.model.entities.Product;
import com.bot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static com.bot.model.Category.categoriesKeyboard;

@Service
public class DeleteCommandService {

    @Autowired
    private ProductRepository productRepository;

    public InlineKeyboardMarkup deleteCommand(String[] arguments, User user, StringBuilder message){
        switch (arguments.length){
            case 0:
                message.append("Выберите категорию:");
                return categoriesKeyboard();
            case 1:
                message.append("Введите цену");
                return new InlineKeyboardMarkup();
            case 2: {
                int price = Integer.parseInt(arguments[1]);
                deleteProduct(arguments, price, message);
                return new InlineKeyboardMarkup();
            }
        }
        return new InlineKeyboardMarkup();
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
}
