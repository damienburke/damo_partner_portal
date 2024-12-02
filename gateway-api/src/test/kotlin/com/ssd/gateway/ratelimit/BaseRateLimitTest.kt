package com.ssd.gateway.ratelimit

import com.fasterxml.jackson.databind.ObjectMapper
import com.ssd.gateway.BaseIT
import com.ssd.gateway.baseUrl
import kotlinx.coroutines.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

abstract class BaseRateLimitTest : BaseIT() {

    val apiResponses = mutableListOf<ApiResponse>()

    companion object {
        @BeforeAll
        @JvmStatic
        fun setup() {
            // Dirty hack, but this allows the available tokens replenish that were used by other tests..
            TimeUnit.SECONDS.sleep(3)
        }
    }

    @Test
    fun `hammer API`() {

        runBlocking {

            hammerApi(getRequestCount(), getDelayMillis())

            // Print Results
            apiResponses.forEach { println("$it") }
            apiResponses.groupingBy { it.responseCode }.eachCount().forEach { (code, count) ->
                println("$code Count: $count")
            }

            val minTime = apiResponses.minOf { it.requestTime }
            val maxTime = apiResponses.maxOf { it.requestTime }

            val duration = Duration.between(minTime, maxTime)
            println("Time difference: ${duration.seconds} seconds")

            assertThat(apiResponses.any { it.responseCode == 429 }).isEqualTo(has429())
            assertThat(apiResponses.any { it.responseCode == 200 }).isEqualTo(has200())

            apiResponses.forEach {
                if (it.responseCode == 429) {
                    assertThat(it.remainingRateLimitTokens == 0).isTrue
                }
            }
        }
    }

    /**
     * Total number of requests to make
     */
    abstract fun getRequestCount(): Int

    /**
     * If 0, the requests will be made in parallel. If non-zero, each request will be sequential, with this delay
     * between them.
     */
    abstract fun getDelayMillis(): Long

    /**
     * Indicates if a single 429 occurred
     */
    abstract fun has429(): Boolean

    /**
     * Indicates if a single 200 occurred
     */
    abstract fun has200(): Boolean

    suspend fun hammerApi(requestCount: Int, delayMillis: Long) = coroutineScope {
        repeat(requestCount) { index ->
            launch {
                try {
                    val response = makeApiRequest(index)
                    apiResponses.add(response)
                } catch (e: Exception) {
                    println("Request #${index + 1} failed: ${e.message}. Should NEVER happen")
                    throw e
                }
            }
            delay(delayMillis)
        }
    }

    private suspend fun makeApiRequest(index: Int): ApiResponse {
        // Use withContext to offload the blocking I/O call
        return withContext(Dispatchers.IO) {

            val request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("${baseUrl}:$port/get?index=$index"))
                .build()

            val requestTime = LocalDateTime.now()
            val response = httpClient.sendAsync(request, BodyHandlers.ofString()).get()

            ApiResponse(
                remainingRateLimitTokens = response.headers().firstValue("X-RateLimit-Remaining").get().toInt(),
                responseCode = response.statusCode(),
                requestTime = requestTime,
                index = index
            )
        }
    }

}

data class ApiResponse(
    val index: Int,
    val responseCode: Int,
    val remainingRateLimitTokens: Int,
    val requestTime: LocalDateTime
)