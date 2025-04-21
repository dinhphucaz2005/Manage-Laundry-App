buildscript {
    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
        classpath(libs.google.services)
    }
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.jetbrains.kotlin.serialization) apply false
    alias(libs.plugins.com.google.devtools.ksp) apply false
}