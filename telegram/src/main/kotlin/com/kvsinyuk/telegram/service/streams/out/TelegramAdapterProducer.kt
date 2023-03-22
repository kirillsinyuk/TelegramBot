package com.kvsinyuk.telegram.service.streams.out

import com.kvsinyuk.plannercoreapi.model.kafka.CommandType
import com.kvsinyuk.plannercoreapi.model.kafka.cmd.TelegramAdapterDataCmd

interface TelegramAdapterProducer {

    fun produce(cmd: TelegramAdapterDataCmd, type: CommandType)
}