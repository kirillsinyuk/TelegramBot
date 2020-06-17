package com.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductCommonCommandService {

    /**
     * в отсутствие активности в течение 30 минут heroku усыпляет приложение.
     */
    @Scheduled(fixedDelay = 1500000)
    public void scheduledTask() {
        log.info("Bot scheduled action every 25 minutes");
    }

}
