package com.example

import com.example.plugins.configureRouting
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*


class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication(Application::configureRouting) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("Hello World!", response.content)
            }
        }
    }
}