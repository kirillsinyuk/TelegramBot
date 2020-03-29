package com.bot.service.commandService;

import com.bot.commands.commonCommands.HelpCommand;
import com.bot.model.entities.Product;
import com.bot.repositories.ProductRepository;
import com.bot.service.converter.ArgsToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static com.bot.model.Category.categoriesKeyboard;

@Service
public class AddCommandService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    HelpCommand helpCommand;

    public InlineKeyboardMarkup addCommand(String[] arguments, User user, StringBuilder message){
        switch (arguments.length){
            case 0:
                message.append("Выберите категорию:");
                return categoriesKeyboard();
            case 1:
                message.append("Введите цену и коммертарий(опционально)");
                return new InlineKeyboardMarkup();
            case 2: {
                int price = Integer.parseInt(arguments[1]);
                createAndSaveProduct(arguments, user, price, message);
                return helpCommand.keyboardMarkup();
            }
            default: {
                int price = Integer.parseInt(arguments[1]);
                createAndSaveProduct(arguments, user, price, message);
                return helpCommand.keyboardMarkup();
            }
        }
    }

    private void createAndSaveProduct(String[] arguments, User user, int price,  StringBuilder message) throws IllegalArgumentException {
        Product product = ArgsToEntityConverter.toProduct(arguments, price, user.getFirstName());
        productRepository.save(product);
        message.append(String.format("Трата %s по цене %s успешно добавлена.", arguments[0], arguments[1]));
    }

}
