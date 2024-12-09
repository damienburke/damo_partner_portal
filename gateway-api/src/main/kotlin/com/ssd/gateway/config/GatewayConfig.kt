package com.ssd.gateway.config

import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GatewayConfig {

    /** List CSP HTTP Headers */
    val cspHeaders = ArrayList<String>()

    init {
        cspHeaders.add("Content-Security-Policy") // Defined by W3C Specs as standard header,
        cspHeaders.add("X-Content-Security-Policy") // Used by Firefox until version 23, and Internet Explorer version 10
        cspHeaders.add("X-WebKit-CSP") // Used by Chrome until version 25
    }

    @Bean
    fun globalFilter(): GlobalFilter = GlobalFilter { exchange, chain ->
        exchange.response.headers.apply {

            add("X-Frame-Options", "DENY")
            add("X-Content-Type-Options", "nosniff")
            add("Referrer-Policy", "strict-origin-when-cross-origin")
            add("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload")
            add("Permissions-Policy", "geolocation=(), camera=(), microphone=()")
            add("X-Permitted-Cross-Domain-Policies", "none")

            cspHeaders.forEach {
                add(it, "script-src 'self'")
            }
        }
        chain.filter(exchange)
    }
}