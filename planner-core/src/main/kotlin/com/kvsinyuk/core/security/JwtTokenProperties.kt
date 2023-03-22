package com.kvsinyuk.core.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "security")
class JwtTokenProperties{

    var token: String = ""
}
