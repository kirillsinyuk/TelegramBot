package com.bot.model;


import com.bot.commands.adminCommands.AddUserCommand;
import com.bot.commands.commonCommands.AddSpendingCommand;
import com.bot.commands.commonCommands.DeleteCommand;
import com.bot.commands.commonCommands.GetAllPurchasesCommand;
import com.bot.commands.commonCommands.GetCategories;
import com.bot.commands.commonCommands.GetStatistic;
import com.bot.commands.commonCommands.HelpCommand;
import com.bot.commands.commonCommands.StartCommand;
import com.bot.service.BotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class Bot extends TelegramLongPollingCommandBot {

    @Autowired
    private AddSpendingCommand addSpendingCommand;
    @Autowired
    private DeleteCommand deleteCommand;
    @Autowired
    private StartCommand startCommand;
    @Autowired
    private HelpCommand helpCommand;
    @Autowired
    private GetCategories categoriesCommand;
    @Autowired
    private AddUserCommand addUserCommand;
    @Autowired
    private GetAllPurchasesCommand getAllPurchasesCommand;
    @Autowired
    private GetStatistic getStatistic;
    @Autowired
    private BotService botService;

    private static final String BOT_NAME = "budget_planner_bot";
    private static final String BOT_TOKEN = "991064577:AAHtI9JsNg8mgR5IRNiveJMLxN9JpZMw4v8";

    public Bot(DefaultBotOptions botOptions) {
        super(botOptions);
    }


    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */
    public String getBotUsername() {
        return BOT_NAME;
    }


    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    /**
     * Обработчик сообщений, не являющихся командами
     * @param update
     */
    @Override
    public void processNonCommandUpdate(Update update) {
        log.info("Processing non-command update...");

        if (!update.hasMessage()) {
            log.error("Update doesn't have a body!");
            throw new IllegalStateException("Update doesn't have a body!");
        }

        Message msg = update.getMessage();
        User user = msg.getFrom();

        log.info("User {} id: {} is trying to send non command message", user.getFirstName() + " " + user.getLastName(), user.getId());

        //TODO проверка на право возможности откравки сообщения

        String clearMessage = msg.getText();
        String messageForUsers = String.format("%s:\n%s", user.getUserName(), clearMessage);

        SendMessage answer = new SendMessage();

       // отправка сообщения всем остальным пользователям бота
        answer.setText(messageForUsers);
        botService.getAccessBotUserList().stream()
                .filter(a -> !a.equals(botService.getUserById(user.getId())))
                .filter(a -> a.getChat() != null)
                .forEach(a -> {
                    answer.setChatId(a.getChat().getId());
                    sendMessageToUser(answer, botService.getUserById(a.getId()).getTlgUser(), user);
                });
    }

    /**
     * Регистрация команд бота. необходимо выволнить после создания экземпляра
     */
    @PostConstruct
    public void registerCommands() {
        log.info("Initializing Planner Bot...");
        register(helpCommand);
        log.info("/help command initialized.");
        register(startCommand);
        log.info("/start command initialized.");
        register(addSpendingCommand);
        log.info("/add command initialized.");
        register(deleteCommand);
        log.info("/delete command initialized.");
        register(addUserCommand);
        log.info("/addUser command initialized.");
        register(getAllPurchasesCommand);
        log.info("/getProd command initialized.");
        register(getStatistic);
        log.info("/getStats command initialized.");
        register(categoriesCommand);
        log.info("/cat command initialized.");

        // Registering default action'...
        registerDefaultAction(((absSender, message) -> {

            log.warn("BotUser {} is trying to execute unknown command '{}'.", message.getFrom().getId(), message.getText());

            SendMessage text = new SendMessage();
            text.setChatId(message.getChatId());
            text.setText(message.getText() + " command not found!");

            try {
                absSender.execute(text);
            } catch (TelegramApiException e) {
                log.error("Error while replying unknown command to user {}.", message.getFrom(), e);
            }

            helpCommand.execute(absSender, message.getFrom(), message.getChat(), new String[] {});
        }));
        log.info("Unknown action command initialized.");
    }

    private void sendMessageToUser(SendMessage message, User receiver, User sender) {
        try {
            execute(message);
            log.info("Sended message from {} to {}", sender.getUserName(), receiver.getUserName());
        } catch (TelegramApiException e) {
            log.error("An error has occurred while trying to send message from {} to {}. Stacktrace:\n {}", sender.getUserName(), receiver.getUserName(), e);
        }
    }

}
