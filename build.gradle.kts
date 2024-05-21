plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0" apply false
}
dependencies {
    implementation(libs.kotlinx.serialization.json)
}
