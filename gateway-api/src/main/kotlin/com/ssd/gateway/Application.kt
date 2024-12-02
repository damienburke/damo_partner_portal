package com.ssd.gateway

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}

@SpringBootApplication
@EnableConfigurationProperties
@RestController
class Application {

    @RequestMapping("/fallback")
    fun fallback(): Mono<String?> {
        return Mono.just<String?>("fallback")
    }
}