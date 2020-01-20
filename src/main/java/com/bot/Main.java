package com.bot;

import com.bot.model.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@SpringBootApplication
public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private static final String PROXY_HOST = "198.27.75.152";
    private static final int PROXY_PORT = 1080;


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {

            LOG.info("Configuring com.bot.model options...");
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

            botOptions.setProxyHost(PROXY_HOST);
            botOptions.setProxyPort(PROXY_PORT);
            botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

            telegramBotsApi.registerBot(Bot.getInstance(botOptions));
            LOG.info( "Регистрация прошла успешно!");
        } catch (TelegramApiRequestException e) {
            LOG.error( "Ошибка регистрации бота:", e.fillInStackTrace());
        }
    }
}
