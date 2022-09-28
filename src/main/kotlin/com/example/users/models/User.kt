package com.example.users.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

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
)

object Users : Table() {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val name: Column<String> = varchar("name", 100)
    val email: Column<String> = varchar("email", 100)
    val password: Column<String> = varchar("password", 100)
    val active: Column<Boolean> = bool("active")
    val roleId: Column<Int> = integer("roleid")
}