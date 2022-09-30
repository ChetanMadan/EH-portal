package com.example.users.models

import io.ktor.auth.Principal
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import java.sql.Date

enum class Role(val roleId: Int) {
    SUPERADMIN(0),
    ADMIN(1),
    USER(2);

    companion object {
        fun toRole(roleId: Int): Role {

            return when (roleId) {
                0 -> SUPERADMIN
                1 -> ADMIN
                2 -> USER
                else -> throw Exception("Invalid roleId specified")
            }
        }
    }
}

data class User(
    val name: String,
    val email: String,
    val active: Boolean,
    val password: String,
    val roleId: Role = Role.USER
): Principal

object Users : Table() {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val name: Column<String> = varchar("name", 100)
    val email: Column<String> = varchar("email", 100)
    val password: Column<String> = varchar("password", 100)
    val address: Column<String> = varchar("address", 500)
    val dateOfBirth: Column<DateTime> = datetime("dob")

    val status: Column<Int> = integer("status")
    val phoneNumber: Column<String> = varchar("phnumber", 10)
    val location: Column<String> = varchar("location", 20)
    val role: Column<Int> = integer("roleid")
    val lastSOSTimeStamp: Column<DateTime> = datetime("lastsos")
    val active: Column<Boolean> = bool("active")


    val roleId: Column<Int> = integer("roleid")
}