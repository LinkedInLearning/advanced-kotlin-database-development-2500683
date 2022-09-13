import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "me.soshin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val exposedVersion = "0.39.2"
val postgresqlVersion = "42.4.1"
dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}