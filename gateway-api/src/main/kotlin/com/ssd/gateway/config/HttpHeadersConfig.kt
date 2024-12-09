package com.ssd.gateway.config

import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HttpHeadersConfig {

    private val cspHeaders = ArrayList<String>()

    init {
        /**
         * DO NOT use X-Content-Security-Policy or X-WebKit-CSP. Their implementations are obsolete (since Firefox 23,
         * Chrome 25), limited, inconsistent, and incredibly buggy.
         * https://cheatsheetseries.owasp.org/cheatsheets/Content_Security_Policy_Cheat_Sheet.html#warning
         */
        cspHeaders.add("Content-Security-Policy") // Defined by W3C Specs as standard header,
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
                add(it, "default-src 'self'; img-src 'self' https://upload.wikimedia.org; report-uri /csp-report")
            }
        }
        chain.filter(exchange)
    }
}