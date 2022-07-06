plugins {
    // Springboot
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    // Kotlin
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

// Jdk1.8
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

// https://github.com/kotest/kotest/issues/279
tasks.withType<Test> {
    useJUnitPlatform()
}

// https://github.com/gradle/gradle/issues/14889
tasks.register("prepareKotlinBuildScriptModel") {}

repositories {
    mavenCentral()
}

dependencies {
    ////////////////////////////////////////////////////////////////////
    // Common
    ////////////////////////////////////////////////////////////////////
    // User Kotlin and JDK8
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // Springboot
    implementation("org.springframework.boot:spring-boot-starter-web")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    ////////////////////////////////////////////////////////////////////
    // Module Only
    ////////////////////////////////////////////////////////////////////
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.h2database:h2")

    // Springboot Quartz
    implementation("org.springframework.boot:spring-boot-starter-quartz")
}