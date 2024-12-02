package com.ssd.gateway.ratelimit

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.Objects

/**
 * https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway/gatewayfilter-factories/requestratelimiter-factory.html
 */
@Component
class ProductKeyResolver : KeyResolver {
    override fun resolve(exchange: ServerWebExchange): Mono<String?> {
        LOG.debug(
            "Request from {}",
            Objects.requireNonNull<String?>(exchange.request.localAddress!!.hostName)
        )
        return Mono.just<String?>(
            Objects.requireNonNull<String?>(
                exchange.request.localAddress!!.hostName
            )
        )
    }
}

private val LOG: Logger = LoggerFactory.getLogger(ProductKeyResolver::class.java)