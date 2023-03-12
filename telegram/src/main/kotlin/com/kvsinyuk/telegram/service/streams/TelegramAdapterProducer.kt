package com.kvsinyuk.telegram.service.streams

import com.kvsinyuk.plannercoreapi.model.kafka.TelegramAdapterDataCmd

interface TelegramAdapterProducer {

    fun produce(cmd: TelegramAdapterDataCmd)
}