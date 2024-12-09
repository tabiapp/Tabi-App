// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false

}

// Project-level build.gradle.kts
buildscript {
    repositories {
        google()  // Repositori untuk Firebase dan SDK Android
        mavenCentral()
    }
    dependencies {
        // Plugin google-services untuk integrasi Firebase
        classpath("com.google.gms:google-services:4.3.15")
    }
}

allprojects {
    repositories {
        google()  // Repositori untuk Firebase
        mavenCentral()
    }
}

