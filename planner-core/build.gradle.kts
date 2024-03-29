plugins {
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"
    kotlin("kapt") version "1.7.22"
}

group = "com.kvsinyuk"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenLocal()
    mavenCentral()
}

val mapstructVersion = "1.5.3.Final"
val jjwtVersion = "0.11.5"

dependencies {
    implementation(project(":planner-core-api"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.cloud:spring-cloud-stream:4.0.1")
    implementation("org.springframework.cloud:spring-cloud-starter-stream-kafka:4.0.1")


    kapt("org.hibernate:hibernate-jpamodelgen:6.1.7.Final")
    kapt("org.mapstruct:mapstruct-processor:$mapstructVersion")
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    implementation("com.google.protobuf:protobuf-java:3.25.0")
    implementation("com.google.protobuf:protobuf-java-util:3.25.0")
    implementation("com.google.api.grpc:proto-google-common-protos:2.28.0")

    // JWT
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    implementation("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    implementation("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")

    // metrics
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")

    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")

    // database
    implementation("org.liquibase:liquibase-core")
    runtimeOnly("org.postgresql:postgresql")

    // test
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("org.testcontainers:postgresql:1.19.2")
    testImplementation("org.testcontainers:junit-jupiter:1.19.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.cloud:spring-cloud-stream-test-support:4.0.1")
    testImplementation("org.springframework.cloud:spring-cloud-contract-wiremock:4.0.1")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

springBoot {
    mainClass.set("com.kvsinyuk.core.MainKt")
}
