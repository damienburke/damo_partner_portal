package com.ssd.gateway.web

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/csp-report")
class CspViolationReportController {

    @PostMapping(consumes = ["application/csp-report"])
    fun reportCSPViolation(@RequestBody report: String?) =
        LOG.warn("A CSP violation has occurred: $report").run {
            ResponseEntity.ok().build<String>()
        }
}

private val LOG: Logger = LoggerFactory.getLogger(CspViolationReportController::class.java)