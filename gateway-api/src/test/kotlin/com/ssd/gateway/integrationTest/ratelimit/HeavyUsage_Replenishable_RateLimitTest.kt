package com.ssd.gateway.integrationTest.ratelimit

import org.springframework.boot.test.context.SpringBootTest

/**
 *
 * 1000 / 80 = 12.5, i.e. ~13 calls every second
 * 50 calls takes ~4 seconds..
 *
 * Replenish Rate is 10
 *
 * 0 Seconds: 20    Initial Capacity
 * 1 Seconds: 17    7 (20-13) left + 10 Replenished
 * 2 Seconds: 14    4 (17-13) left + 10 Replenished
 * 3 Seconds: 11    1 (14-13) left + 10 Replenished
 * 4 Seconds: 8    -2 (11-13) left + 10 Replenished
 * 5 Seconds: 5    -5 (8-13) left  + 10 Replenished
 * 6 Seconds: 2    -8 (5-13) left  + 10 Replenished
 * 7 Seconds: -1    -11 (2-13) left + 10 Replenished == 429
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HeavyUsage_Replenishable_RateLimitTest : BaseRateLimitTest() {

    override fun getRequestCount() = 50

    /**
     * Delay between each call, so available tokens can be replenished
     */
    override fun getDelayMillis() = 80L

    override fun has429() = false

    override fun has200() = true
}