package com.example.sos.routes

import com.example.common.initializeMandatoryParameters
import com.example.users.services.UserService
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

object SOSRoute {

    fun Route.sos(userService: UserService) {
        route("/disableSOS") {
            get("") {
                val userId = initializeMandatoryParameters(call, "userId").toInt()
                userService.disableSOSbyUserId(userId)
                call.respond(userId)
            }
        }
    }
}