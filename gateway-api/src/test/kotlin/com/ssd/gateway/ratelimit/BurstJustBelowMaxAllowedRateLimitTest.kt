package com.ssd.gateway.ratelimit

import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BurstJustBelowMaxAllowedRateLimitTest : BaseRateLimitTest() {

    /**
     * Just below the burstCapacity of 20
     */
    override fun getRequestCount() = 19

    /**
     * No delay, so the requests will all be concurrent
     */
    override fun getDelayMillis() = 0L

    override fun has429() = false

    override fun has200() = true
}