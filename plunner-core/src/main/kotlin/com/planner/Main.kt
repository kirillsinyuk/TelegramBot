package com.planner

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Main

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    SpringApplication.run(Main::class.java, *args)
}
