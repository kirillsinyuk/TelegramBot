package com.bot;

import com.bot.commands.context.Context;
import com.bot.model.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@SpringBootApplication
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private static final String PROXY_HOST = "198.27.75.152";
    private static final int PROXY_PORT = 1080;


    public static void main(String[] args) {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(Context.class);
        try {
            TelegramBotsApi telegramBotsApi = ctx.getBean(TelegramBotsApi.class);
            telegramBotsApi.registerBot(ctx.getBean(Bot.class));
            LOG.info( "Регистрация прошла успешно!");
        } catch (TelegramApiRequestException e) {
            LOG.error( "Ошибка регистрации бота:", e.fillInStackTrace());
        }
    }
}
