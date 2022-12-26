package com.bot.service

import mu.KLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class TimeSchedulerService {

    /** Heroku shut down the app with no activity in 25 minutes */
    @Scheduled(fixedDelay = 1500000)
    fun scheduledTask() {
        logger.info("Bot scheduled action every 25 minutes")
    }

    companion object: KLogging()
}