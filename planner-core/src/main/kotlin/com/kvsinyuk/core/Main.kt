package com.kvsinyuk.core

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication
@EnableConfigurationProperties
class Main

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    SpringApplication.run(Main::class.java, *args)
}
