package com.bot.context;

import com.bot.model.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.sql.DataSource;


@Configuration
public class AppContextConfiguration {


    @Bean
    public TelegramBotsApi getTlgBot(Bot bot, Logger LOG){
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            LOG.error( "Ошибка регистрации бота:", e.fillInStackTrace());
        }
        return telegramBotsApi;
    }

//    @Bean
//    public DataSource getDataSource() {
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
//        dataSourceBuilder.url("jdbc:mysql://localhost:3306/budget_planner?serverTimezone=UTC");
//        dataSourceBuilder.username("test_dev");
//        dataSourceBuilder.password("longpassword");
//        return dataSourceBuilder.build();
//    }

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger("appLogger");
    }

    @Bean
    public DefaultBotOptions getDefaultBotOptions() {
        ApiContextInitializer.init();
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        String PROXY_HOST = "198.27.75.152";
        int PROXY_PORT = 1080;
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
