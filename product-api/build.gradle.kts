plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "2.0.21"
    kotlin("plugin.allopen") version "1.9.22"
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    id("io.snyk.gradle.plugin.snykplugin") version "0.7.0"
    id("com.google.cloud.tools.jib") version "3.4.4"
}

group = "com.ssd"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

ext {
    set("testcontainers.version", "1.19.8")
    set("flywayPostgresqlVersion", "10.21.0")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
//    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("org.flywaydb:flyway-database-postgresql:${property("flywayPostgresqlVersion")}")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

snyk {
    setArguments("--all-sub-projects")
    setSeverity("high")
    setAutoDownload(true)
    setAutoUpdate(true)
}

tasks.test {
    finalizedBy(tasks.`snyk-test`)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jib {
    from {
        image = "gcr.io/distroless/java21-debian12"
    }
    to {
        image = "product-api:0.0.1-SNAPSHOT"
    }
    container {
        mainClass = "com.ssd.ProductApiApplicationKt"
        jvmFlags = listOf("-Xmx2048M")
        ports = listOf("8080", "8081")
    }
}