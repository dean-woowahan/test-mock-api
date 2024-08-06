package com.jyh.testweb.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import kotlin.system.measureTimeMillis

@RestController
class TestController {

    val lock = Object()

    @GetMapping("/mock-delay")
    fun mockDelay(): TestResponse {
        log.info("호출됨")
        synchronized(lock) {
            lock.wait()
        }
        log.info("완료됨")
        return TestResponse("success mock-delay")
    }

    @GetMapping("/release-mock-delay")
    fun releaseMockDelay(): TestResponse {
        synchronized(lock) {
            lock.notify()
        }
        return TestResponse("success release-mock-delay")
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    data class TestResponse(
        val text: String
    )
}