package com.example.modules

import com.example.users.services.UserService
import io.ktor.application.Application
import io.ktor.application.install
import org.koin.dsl.module
import org.koin.ktor.ext.Koin


fun Application.dependencyInjectionModule() {
    install(Koin) {
        modules(koinModule())
    }
}

fun koinModule() = module {
    single { UserService() }
}
