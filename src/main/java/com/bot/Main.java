package com.bot;

import com.bot.commands.context.Context;
import com.bot.model.Bot;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@SpringBootApplication
public class Main {

    @Autowired
    private static Logger LOG;

    public static void main(String[] args) {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext(Context.class);
        try {
            TelegramBotsApi telegramBotsApi = ctx.getBean(TelegramBotsApi.class);
            telegramBotsApi.registerBot(ctx.getBean(Bot.class));
        } catch (TelegramApiRequestException e) {
            LOG.error( "Ошибка регистрации бота:", e.fillInStackTrace());
        }
    }
}
