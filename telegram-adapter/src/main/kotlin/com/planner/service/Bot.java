package com.planner.service;

import com.planner.service.botHandlers.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.api.objects.*;


@Component
public class Bot extends TelegramLongPollingBot {

    @Autowired
    private CommandBotHandler commandBotHandler;
    @Autowired
    private MenuBotHandler menuBotHandler;

    @Value("${bot.name}")
    private String BOT_NAME;
    @Value("${bot.token}")
    private String BOT_TOKEN;

    //TODO похоже ничего другого не придумать.
    // как узнать, что пользователь ответил сообщением на команду? или просто другую коману ввел.
    private Map<Long, Update> previousUpdates;

    public Bot(DefaultBotOptions botOptions) {
        super(botOptions);
        previousUpdates = new HashMap<>();
    }

    @Override
    public void onUpdateReceived(Update update) {
        emptyBodyCheck(update);

        Long chatId = getChatId(update);
        Update previousUpdate = previousUpdate(chatId);

        if(isTextCommand(update, previousUpdate)) {
            commandBotHandler.handle(this, update, update.getMessage());
        } else if(isMenuTap(update) || isAnswerOnCommand(update, previousUpdate)) {
            Update currentUpdate = isMenuTap(update) ? update : previousUpdate;
            menuBotHandler.handle(this, currentUpdate, update.getMessage());
        }
        previousUpdates.put(chatId, update);
    }

    private void emptyBodyCheck(Update update) {
        if (!update.hasMessage() && !update.hasCallbackQuery()) {
            log.error("Update doesn't have a body!\n" + update.toString());
            throw new IllegalStateException("Update doesn't have a body!");
        }
    }

    private Long getChatId(Update update) {
        Long chatId = update.hasCallbackQuery() ? update.getCallbackQuery().getMessage().getChatId() : null;
        chatId = update.hasMessage() ? update.getMessage().getChatId() : chatId;
        return chatId;
    }

    private Update previousUpdate(Long chatId) {
        Update previousUpdate = null;
        if (chatId != null) {
            previousUpdate = previousUpdates.get(chatId);
        }
        return previousUpdate;
    }

    private boolean isTextCommand(Update update, Update previousUpdate) {
        return update.hasMessage() && !update.hasCallbackQuery() && !isAnswerOnCommand(update, previousUpdate);
    }

    private boolean isMenuTap(Update update) {
        return update.hasCallbackQuery();
    }

    private boolean isAnswerOnCommand(Update update, Update previousUpdate) {
        return update.getMessage() != null && isContactOrText(update) && previousUpdate != null && previousUpdate.hasCallbackQuery();
    }

    private boolean isContactOrText(Update update) {
        return update.getMessage().hasContact() || update.getMessage().hasText();
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

//    private void sendMessageToUser(SendMessage message, User receiver, User sender) {
//        try {
//            execute(message);
//            log.info("Sended message from {} to {}", sender.getUserName(), receiver.getUserName());
//        } catch (TelegramApiException e) {
//            log.error("An error has occurred while trying to send message from {} to {}. Stacktrace:\n {}", sender.getUserName(), receiver.getUserName(), e);
//        }
//    }

}
