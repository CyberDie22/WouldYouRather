import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.5.20-M1"
    application
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "me.cyberdie22"
version = "1.0.0"
val main = "MainKt"

repositories {
    mavenCentral()
}

dependencies {

}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "16"
    }

    withType<ShadowJar> {
        minimize()
        manifest {
            attributes["Main-Class"] = main
        }
    }
}

application {
    // Required for Shadow to work correctly I guess
    @Suppress("DEPRECATION")
    mainClassName = main
    mainClass.set(main)
}