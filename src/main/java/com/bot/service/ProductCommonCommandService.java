package com.bot.service;

import com.bot.model.Action;
import com.bot.model.Category;
import com.bot.service.commandService.AddCommandService;
import com.bot.service.commandService.DeleteCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Arrays;

@Slf4j
@Service
public class ProductCommonCommandService {

    @Autowired
    AddCommandService addCommandService;
    @Autowired
    DeleteCommandService deleteCommandService;


    public InlineKeyboardMarkup commonAction(String[] arguments, User user, StringBuilder message, Action action) {
        try {
            switch (action){
                case ADD:
                    return addCommandService.addCommand(arguments, user, message);
                case DELETE:
                    return deleteCommandService.deleteCommand(arguments, user, message);
            }

        } catch (NumberFormatException e){
            message.append("Неверный формат команды! Используйте команду /help");
            log.error(String.format("Неверный формат ввода от пользователя %s id: %d", user.getFirstName(), user.getId()), e);

        } catch (IllegalArgumentException e){
            message.append("Категория не найдена! Список доступных категорий:\n");
            Arrays.stream(Category.values()).forEach(x -> message.append(x.getName()).append("\n"));
            log.error(String.format("Попытка добавления неизвестной категории от пользователя %s id: %d", user.getFirstName(), user.getId()), e);
        }
        return null;
    }

    /**
     * в отсутствие активности в течение 30 минут heroku усыпляет приложение.
     */
    @Scheduled(fixedDelay = 1500000)
    public void scheduledTask() {
        log.info("Bot scheduled action every 25 minutes");
    }

}
