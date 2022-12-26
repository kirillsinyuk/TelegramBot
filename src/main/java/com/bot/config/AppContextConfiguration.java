package com.bot.config;

import com.bot.model.Bot;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${proxy.host}")
    private String PROXY_HOST;
    @Value("${proxy.port}")
    private int PROXY_PORT;

    @Bean
    public TelegramBotsApi getTlgBot(Bot bot){
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(bot);
            log.info( "Регистрация прошла успешно!");
        } catch (TelegramApiRequestException e) {
            log.error( "Ошибка регистрации бота:", e.fillInStackTrace());
        }
        return telegramBotsApi;
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
