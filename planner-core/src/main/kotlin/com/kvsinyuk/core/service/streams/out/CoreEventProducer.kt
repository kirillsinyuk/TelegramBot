package com.kvsinyuk.core.service.streams.out

import com.kvsinyuk.plannercoreapi.model.kafka.CommandType
import com.kvsinyuk.plannercoreapi.model.kafka.event.CoreEvent

interface CoreEventProducer {

    fun produce(cmd: CoreEvent, type: CommandType)
}
