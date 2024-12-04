package com.ssd.gateway.config

import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GatewayConfig {

    @Bean
    fun globalFilter(): GlobalFilter = GlobalFilter { exchange, chain ->
        exchange.response.headers.apply {
            add("X-Frame-Options", "DENY")
            add("X-Content-Type-Options", "nosniff")
            add("Referrer-Policy", "strict-origin-when-cross-origin")
            add("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload")
            add("Permissions-Policy", "geolocation=(), camera=(), microphone=()")
            add("X-Permitted-Cross-Domain-Policies", "none")
        }
        chain.filter(exchange)
    }
}