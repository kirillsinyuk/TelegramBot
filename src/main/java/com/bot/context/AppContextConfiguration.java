package com.bot.context;

import com.bot.model.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Configuration
@EnableScheduling
public class AppContextConfiguration {

    private static final String PROXY_HOST = "79.110.164.22";
    private static final int PROXY_PORT = 8080;

    @Bean
    public TelegramBotsApi getTlgBot(Bot bot, Logger LOG){
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(bot);
            LOG.info( "Регистрация прошла успешно!");
        } catch (TelegramApiRequestException e) {
            LOG.error( "Ошибка регистрации бота:", e.fillInStackTrace());
        }
        return telegramBotsApi;
    }

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger("appLogger");
    }

    @Bean
    public DefaultBotOptions getDefaultBotOptions() {
        ApiContextInitializer.init();
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        botOptions.setProxyHost(PROXY_HOST);
        botOptions.setProxyPort(PROXY_PORT);
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        return botOptions;
    }

    @Bean
    public TelegramBotsApi getBotsApi() {
        return new TelegramBotsApi();
    }
}
