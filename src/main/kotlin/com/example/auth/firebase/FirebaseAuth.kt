package com.example.auth.firebase

import com.google.firebase.ErrorCode
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.auth.Authentication
import io.ktor.auth.AuthenticationPipeline
import io.ktor.auth.AuthenticationProvider
import io.ktor.auth.Principal
import io.ktor.http.HttpStatusCode
import io.ktor.request.ApplicationRequest
import io.ktor.request.authorization
import io.ktor.response.respond
import org.slf4j.LoggerFactory


private val firebaseAuthLogger = LoggerFactory.getLogger("io.robothouse.auth.firebase")

class FirebaseAuthenticationProvider internal constructor(config: Configuration) : AuthenticationProvider(config) {

    internal val token: (ApplicationCall) -> String? = config.token
    internal val principle: ((uid: String) -> Principal?)? = config.principal

    class Configuration internal constructor(name: String?) : AuthenticationProvider.Configuration(name) {

        internal var token: (ApplicationCall) -> String? = { call -> call.request.parseAuthorizationToken() }

        internal var principal: ((uid: String) -> Principal?)? = null

        internal fun build() = FirebaseAuthenticationProvider(this)
    }
}

fun Authentication.Configuration.firebase(
    name: String? = null,
    configure: FirebaseAuthenticationProvider.Configuration.() -> Unit
) {
    val provider = FirebaseAuthenticationProvider.Configuration(name).apply(configure).build()
    provider.pipeline.intercept(AuthenticationPipeline.RequestAuthentication) { context ->

        try {
            val token = provider.token(call) ?: throw FirebaseAuthException(
                FirebaseException(
                    ErrorCode.UNAUTHENTICATED,
                    "No token could be found",
                    null
                )
            )

            val uid = FirebaseAuth.getInstance().verifyIdToken(token).uid

            provider.principle?.let { it.invoke(uid)?.let { principle -> context.principal(principle) } }

        } catch (cause: Throwable) {
            val message = if (cause is FirebaseAuthException) {
                "Authentication failed: ${cause.message ?: cause.javaClass.simpleName}"
            } else {
                cause.message ?: cause.javaClass.simpleName
            }
            firebaseAuthLogger.trace(message)
            call.respond(HttpStatusCode.Unauthorized, message)
            context.challenge.complete()
            finish()
        }
    }
    register(provider)
}

fun ApplicationRequest.parseAuthorizationToken(): String? = authorization()?.let {
    it.split(" ")[1]
}