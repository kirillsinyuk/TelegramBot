package com.kvsinyuk.telegram.config

import com.google.protobuf.BoolValue
import com.google.protobuf.DoubleValue
import com.google.protobuf.FloatValue
import com.google.protobuf.Int32Value
import com.google.protobuf.Int64Value
import com.google.protobuf.StringValue
import com.google.protobuf.TypeRegistry
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProtobufMessageConverterConfig {

    @Bean
    fun protobufMessageConverter(type: TypeRegistry) = ProtobufMessageConverter(type)

    @Bean
    @ConditionalOnMissingBean(TypeRegistry::class)
    fun defaultTypeRegistry(): TypeRegistry =
        TypeRegistry.newBuilder()
            .add(BoolValue.getDescriptor())
            .add(Int32Value.getDescriptor())
            .add(Int64Value.getDescriptor())
            .add(StringValue.getDescriptor())
            .add(FloatValue.getDescriptor())
            .add(DoubleValue.getDescriptor())
            .build()
}