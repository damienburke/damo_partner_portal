package com.ssd.gateway.ratelimit

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("spring.redis")
class RedisConfig {
    var host = ""
    var port = 0
}
