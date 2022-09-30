package com.example.modules

import com.example.sos.routes.SOSRoute.sos
import com.example.users.routes.UsersRoute.users
import com.example.users.services.UserService
import io.ktor.application.Application
import io.ktor.routing.routing
import org.koin.java.KoinJavaComponent.inject

fun Application.sosRouteApiModule() {
    val userService: UserService by inject(UserService::class.java)

    routing {
        sos(userService)
    }
}