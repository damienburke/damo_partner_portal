package com.ssd.gateway.ratelimit

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

@Configuration
class SpringRedisConfig {

    @Bean
    fun connectionFactory(redisConfig: RedisConfig): LettuceConnectionFactory {

        println("redisConfig.host ${redisConfig.host}")

        val redisConf = RedisStandaloneConfiguration()
        redisConf.hostName = redisConfig.host
        redisConf.port = redisConfig.port
        redisConf.database = 0
        return LettuceConnectionFactory(redisConf);
    }
}