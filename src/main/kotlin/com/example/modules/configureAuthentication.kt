package com.example.modules

import com.example.users.routes.UsersRoute.users
import com.example.users.services.UserService
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.authentication
import io.ktor.routing.routing
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.koin.ktor.ext.Koin


fun Application.configureAuthentication() {

    authentication {

    }
}
