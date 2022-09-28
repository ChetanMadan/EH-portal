package com.example.users.services

import com.example.DatabaseFactory.dbQuery
import com.example.users.models.Role
import com.example.users.models.User
import com.example.users.models.Users
import io.ktor.features.BadRequestException
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class UserService {
    suspend fun getAllUsers(): List<User> = dbQuery {
        Users.selectAll().map { toUser(it) }
    }


    private fun toUser(row: ResultRow): User =
        User(row[Users.email], row[Users.name], row[Users.active], row[Users.password], Role.toRole(row[Users.roleId]))

    suspend fun addUser(user: User): Int {
            val newUser = dbQuery {
                Users.insert {
                    it[email] = user.email
                    it[name] = user.name
                    it[active] = user.active
                    it[password] = user.password
                    it[roleId] = user.roleId.roleId
                } get Users.id
            }
            return newUser

    }
}