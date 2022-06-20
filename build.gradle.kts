@Suppress("DSL_SCOPE_VIOLATION") // workaround for IntelliJ bug with Gradle Version Catalogs DSL in plugins
plugins {
    alias(tools.plugins.kotlin.lang)
    alias(tools.plugins.kotlin.kapt)
    alias(tools.plugins.kotlin.allopen)
    alias(tools.plugins.shadow)
    alias(libs.plugins.micronaut)
}

version = "0.1"
group = "tech.zone84.updateoperators"

repositories {
    mavenCentral()
}

dependencies {
    kapt("io.micronaut:micronaut-http-validation")
    kapt("io.micronaut.data:micronaut-data-document-processor")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.data:micronaut-data-mongodb")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.mongodb:micronaut-mongo-sync")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation(tools.kotlin.reflect)
    implementation(tools.kotlin.stdlib)
    implementation(libs.kotlinlogging)
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("org.mongodb:mongodb-driver-sync")
    testImplementation("io.micronaut.test:micronaut-test-kotest5")
    testImplementation("org.testcontainers:mongodb")
    testImplementation("org.testcontainers:testcontainers")
    implementation("io.micronaut:micronaut-validation")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

}


application {
    mainClass.set("tech.zone84.updateoperators.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion(tools.versions.jvm.get())
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = tools.versions.jvm.get()
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = tools.versions.jvm.get()
        }
    }
    test {
        useJUnitPlatform()
    }
}
graalvmNative.toolchainDetection.set(false)
micronaut {
    version(libs.versions.micronaut.get())
    runtime("netty")
    testRuntime("kotest5")
    processing {
        incremental(true)
        annotations("tech.zone84.updateoperators.*")
    }
}
