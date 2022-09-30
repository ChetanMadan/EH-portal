import groovy.xml.dom.DOMCategory.attributes
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val koin_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.5.20"
    id("org.flywaydb.flyway") version "8.5.4"
}


tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.example.ApplicationKt"
    }
}

flyway {
    url = System.getenv("DB_URL")
    user = System.getenv("DB_USER")
    password = System.getenv("DB_PASSWORD")
    baselineOnMigrate = true
//    locations = ["filesystem:resources/db/migration"]
}



group = "com.example"
version = "0.0.1"


tasks.create("stage") {
    dependsOn("installDist")
}


java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
    }
}

application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}


dependencies {
//    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")


    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-jackson:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")
    implementation("io.ktor:ktor-client-gson:$ktor_version")
    implementation("com.natpryce:konfig:1.6.10.0")
    implementation("org.flywaydb:flyway-core:9.2.2")
    implementation("org.postgresql:postgresql:42.5.0")
    implementation("org.jetbrains.exposed:exposed:0.17.14")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("io.insert-koin:koin-ktor:$koin_version")

    implementation("com.google.firebase:firebase-admin:7.1.0")

    implementation("org.simpleframework:simple-xml:2.7.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.13.4")
    implementation("org.json:json:20090211")


    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
    testImplementation("io.insert-koin:koin-test-junit5:$koin_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}