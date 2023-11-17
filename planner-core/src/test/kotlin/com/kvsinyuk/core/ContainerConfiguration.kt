package com.kvsinyuk.core

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import javax.sql.DataSource

@ExtendWith(SpringExtension::class)
@Testcontainers
@TestConfiguration
class ContainerConfiguration {

    @Bean
    fun dataSource(): DataSource? {
        return DataSourceBuilder.create()
            .username(POSTGRES_CONTAINER.username)
            .password(POSTGRES_CONTAINER.password)
            .driverClassName(POSTGRES_CONTAINER.driverClassName)
            .url(POSTGRES_CONTAINER.jdbcUrl)
            .build()
    }

    init {
        POSTGRES_CONTAINER.start()
    }

    companion object {
        @JvmStatic
        @Container
        val POSTGRES_CONTAINER = PostgreSQLContainer(DockerImageName.parse("postgres:12.6"))
            .withDatabaseName("planner")
            .withUsername("postgres")
            .withPassword("mysecretpassword")
    }
}