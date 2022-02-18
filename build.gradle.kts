buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.1")
        classpath(kotlin("gradle-plugin", version = "1.5.30"))
    }
}
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
//plugins {
//    id("com.android.application") version ("7.1.1")
//    id("com.android.library") version ("7.1.1")
//    id("org.jetbrains.kotlin.android") version ("1.6.10")
//}

//task clean (type: Delete) {
//    delete rootProject . buildDir
//}