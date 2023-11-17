package com.kvsinyuk.core.integration

import com.kvsinyuk.core.ContainerConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(classes = [ContainerConfiguration::class])
abstract class AbstractIntegrationTest {
}

