package com.ssd.gateway.integrationTest

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecurityHeadersTest : BaseIT() {

    @Test
    fun `check headers`() {
        val request = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("${baseUrl}:$port/get"))
            .build()

        val response = httpClient.sendAsync(request, BodyHandlers.ofString()).get()

        assertThat(response.headers().allValues("permissions-policy")[0]).isEqualTo("geolocation=(), camera=(), microphone=()")
        assertThat(response.headers().allValues("referrer-policy")[0]).isEqualTo("strict-origin-when-cross-origin")
        assertThat(response.headers().allValues("strict-transport-security")[0]).isEqualTo("max-age=31536000; includeSubDomains; preload")
        assertThat(response.headers().allValues("x-content-type-options")[0]).isEqualTo("nosniff")
        assertThat(response.headers().allValues("x-frame-options")[0]).isEqualTo("DENY")
        assertThat(response.headers().allValues("x-permitted-cross-domain-policies")[0]).isEqualTo("none")
    }
}