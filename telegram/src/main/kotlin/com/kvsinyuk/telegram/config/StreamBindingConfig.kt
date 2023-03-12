package com.kvsinyuk.telegram.config

import com.kvsinyuk.telegram.model.TelegramUser
import com.kvsinyuk.telegram.service.streams.CoreDataConsumer
import com.kvsinyuk.telegram.service.streams.TelegramAdapterEventConsumer
import com.kvsinyuk.telegram.service.streams.TelegramAdapterProducer
import com.kvsinyuk.telegram.service.streams.TelegramCmdProducer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StreamBindingConfig {

    @Bean("telegram-data-cmd")
    fun telegramCommandProducer(): TelegramAdapterProducer =
        TelegramCmdProducer()

    @Bean("core-data-message")
    fun coreMessageConsumer(consumer: TelegramAdapterEventConsumer) =
        CoreDataConsumer(consumer)

    @Bean
    fun myEventConsumer(): TelegramAdapterEventConsumer {
        return object : TelegramAdapterEventConsumer {
            override fun consume(event: TelegramUser) {
                println("Received $event")
            }
        }
    }


}