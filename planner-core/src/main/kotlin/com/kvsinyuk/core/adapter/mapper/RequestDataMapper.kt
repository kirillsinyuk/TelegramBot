package com.kvsinyuk.core.adapter.mapper

import com.kvsinyuk.core.config.MapperConfiguration
import com.kvsinyuk.v1.kafka.RequestDataOuterClass
import org.mapstruct.Mapper


@Mapper(config = MapperConfiguration::class)
abstract class RequestDataMapper {

    abstract fun toRequestDataProto(userId: String, chatId: String): RequestDataOuterClass.RequestData
}
