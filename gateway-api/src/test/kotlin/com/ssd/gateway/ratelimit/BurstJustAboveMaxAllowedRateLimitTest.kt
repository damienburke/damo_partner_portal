package com.ssd.gateway.ratelimit

import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BurstJustAboveMaxAllowedRateLimitTest : BaseRateLimitTest() {

    /**
     * Just above the burstCapacity of 20
     */
    override fun getRequestCount() = 21

    /**
     * No delay, so the requests will all be concurrent
     */
    override fun getDelayMillis() = 0L

    override fun has429() = true

    override fun has200() = true
}