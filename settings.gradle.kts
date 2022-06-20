rootProject.name="updateoperators-demo"

dependencyResolutionManagement {
    versionCatalogs {
        create("tools") {
            version("kotlin", "1.6.21")
            version("jvm", "17")

            plugin("kotlin-lang", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("kotlin-kapt", "org.jetbrains.kotlin.kapt").versionRef("kotlin")
            plugin("kotlin-allopen", "org.jetbrains.kotlin.plugin.allopen").versionRef("kotlin")
            plugin("shadow", "com.github.johnrengelman.shadow").version("7.1.2")

            library("kotlin-reflect", "org.jetbrains.kotlin", "kotlin-reflect").versionRef("kotlin")
            library("kotlin-stdlib", "org.jetbrains.kotlin", "kotlin-stdlib-jdk8").versionRef("kotlin")
        }

        create("libs") {
            version("micronaut", "3.5.1")
            plugin("micronaut", "io.micronaut.application").version("3.4.1")
            library("kotlinlogging", "io.github.microutils:kotlin-logging-jvm:2.1.20")
        }
    }
}
