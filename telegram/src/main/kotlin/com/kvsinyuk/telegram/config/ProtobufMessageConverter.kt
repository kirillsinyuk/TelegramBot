package com.kvsinyuk.telegram.config

import com.google.protobuf.AbstractMessage
import com.google.protobuf.TypeRegistry
import com.google.protobuf.util.JsonFormat
import org.springframework.integration.MessageRejectedException
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders
import org.springframework.messaging.converter.AbstractMessageConverter
import org.springframework.util.ConcurrentReferenceHashMap
import org.springframework.util.MimeType
import java.lang.reflect.Method
import java.nio.charset.StandardCharsets

class ProtobufMessageConverter(
    val typeRegistry: TypeRegistry
) : AbstractMessageConverter(MimeType("message", "protobuf-json")) {

    private val CHARSET = StandardCharsets.UTF_8
    private val cache = ConcurrentReferenceHashMap<Class<*>, Method>()

    override fun supports(clazz: Class<*>): Boolean {
        return AbstractMessage::class.java.isAssignableFrom(clazz)
    }

    override fun convertFromInternal(message: Message<*>, targetClass: Class<*>, conversionHint: Any?): Any? {
        try {
            val builder = getMessageBuilder(targetClass)
            JsonFormat.parser()
                .usingTypeRegistry(typeRegistry)
                .ignoringUnknownFields()
                .merge(String(message.payload as ByteArray, CHARSET), builder)
            return builder.build()
        } catch (ex: Exception) {
            throw MessageRejectedException(message, ex.message!!, ex)
        }
    }

    override fun convertToInternal(payload: Any, headers: MessageHeaders?, conversionHint: Any?): Any? {
        val jsonString = JsonFormat.printer()
            .omittingInsignificantWhitespace()
            .includingDefaultValueFields()
            .usingTypeRegistry(typeRegistry)
            .print(payload as AbstractMessage?)
        return jsonString.toByteArray(CHARSET)
    }

    private fun getMessageBuilder(clazz: Class<*>): com.google.protobuf.Message.Builder {
        try {
            var method: Method? = cache.get(clazz)
            if (method == null) {
                method = clazz.getMethod("newBuilder")
                cache[clazz] = method
            }
            return method!!.invoke(clazz) as com.google.protobuf.Message.Builder
        } catch (ex: Exception) {
            throw RuntimeException("Invalid Protobuf Message type: no invocable newBuilder() method on $clazz", ex)
        }

    }
}