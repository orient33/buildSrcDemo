import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.30"
}

repositories {
    google()
    mavenCentral()
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.apiVersion = "1.4"
}

dependencies {
    implementation("com.android.tools.build:gradle-api:7.1.1")
    implementation(kotlin("stdlib"))
    gradleApi()
    implementation("org.jetbrains.kotlin:kotlin-script-runtime:1.5.30")
}
dependencies {
    implementation("com.android.tools.build:gradle:7.1.1")
    implementation("org.ow2.asm:asm-util:7.0")
}