package com.example.config.firebase

import com.example.auth.firebase.FirebaseAuthenticationProvider
import com.example.users.models.Role
import com.example.users.models.User
import kotlinx.coroutines.runBlocking

object AuthConfig {
    fun FirebaseAuthenticationProvider.Configuration.configure() {
        principal = { uid ->
            //this is where you'd make a db call to fetch your User's profile data
            runBlocking { User(uid, "myUsername", true, "password", Role.SUPERADMIN) }
        }
    }
}