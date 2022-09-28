package com.example

import com.example.modules.dependencyInjectionModule
import com.example.modules.userRouteApiModule
import com.example.plugins.*
import io.ktor.application.Application
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine

fun main() {

    val engine = engine {
        dependencyInjectionModule()
        userRouteApiModule()

        configureSerialization()
        configureSecurity()
        configureRouting()
        DatabaseFactory.init()
    }

    engine.start(wait = true)
}

fun engine(modules: Application.() -> Unit): NettyApplicationEngine {
    val env = applicationEngineEnvironment {
        module {
            modules()
        }
        connector {
            port = 8080
        }
    }
    return embeddedServer(Netty, env)
}
