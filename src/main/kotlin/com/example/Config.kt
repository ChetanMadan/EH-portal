package com.example

import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.Key
import com.natpryce.konfig.intType
import com.natpryce.konfig.overriding
import com.natpryce.konfig.stringType

object Config {

    private val config = EnvironmentVariables() overriding ConfigurationProperties.fromResource("default.properties")

    val dbUser = config.getOrElse(Key("dbUser", stringType), "")
    val dbName = config.getOrElse(Key("dbName", stringType), "")
    val dbPassword = config.getOrElse(Key("dbPassword", stringType), "")
    val dbPort = config.getOrElse(Key("dbPort", intType), 5432)
    val dbHost = config.getOrElse(Key("dbHost", stringType), "")
    val dbUrl = config.getOrElse(
        Key("jdbcUrl", stringType),
        "$dbHost/$dbName?user=$dbUser&password=$dbPassword&sslmode=require"
    )
}