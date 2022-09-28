package com.example.common

import io.ktor.application.ApplicationCall
import io.ktor.features.BadRequestException

fun initializeMandatoryParameters(call: ApplicationCall, parameter: String): String {
    return call.parameters[parameter]
        ?.takeIf { it.isNotBlank() }
        ?: throw BadRequestException("$parameter not provided")
}