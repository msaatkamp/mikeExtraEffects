import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.8.0"
    id("org.jetbrains.kotlin.plugin.serialization") version("1.8.0")
    id("dev.architectury.loom") version("0.12.0-SNAPSHOT")
    id("architectury-plugin") version("3.4-SNAPSHOT")
}

group = "com.mikeretriever.extraeffects"
version = "0.1-SNAPSHOT"

architectury {
    platformSetupLoomIde()
    forge()
}

loom {
    silentMojangMappingsLicense()

    mixin {
        defaultRefmapName.set("mixins.${project.name}.refmap.json")
    }
}

repositories {
    mavenCentral()
    maven(url = "https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
    maven("https://maven.impactdev.net/repository/development/")
    maven(url = "https://thedarkcolour.github.io/KotlinForForge/")
}

dependencies {
    minecraft("net.minecraft:minecraft:1.19.2")
    implementation("thedarkcolour:kotlinforforge:3.10.0")
    forge("net.minecraftforge:forge:1.19.2-43.2.0")
    mappings("net.fabricmc:yarn:1.19.2+build.4:v2")

    modImplementation("dev.architectury", "architectury-forge", "6.5.69")
    modImplementation("com.cobblemon:forge:1.3.0+1.19.2-SNAPSHOT") {
        isTransitive = false
    }

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}