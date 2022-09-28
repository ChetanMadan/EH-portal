package com.example.users.routes

import com.example.common.initializeMandatoryParameters
import com.example.users.models.Role
import com.example.users.models.User
import com.example.users.services.UserService
import io.ktor.application.call
import io.ktor.features.BadRequestException
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

object UsersRoute {
    fun Route.users(userService: UserService) {

        route("/getUsers") {
            get("") {
                call.respond(userService.getAllUsers())
            }
        }

        route("/addUser") {
            get("") {
                User(
                    name = initializeMandatoryParameters(call, "name"),
                    email = initializeMandatoryParameters(call, "email"),
                    password = initializeMandatoryParameters(call, "password"),
                    active = true,
                    roleId = (call.parameters["roleId"] ?: "1")
                        .let { Role.toRole(it.toInt()) }
                ).let {
                    try {
                        call.respond(userService.addUser(it))
                    } catch (e: Exception) {
                        throw BadRequestException("${e.message}")
                    }
                }
            }
        }
    }
}