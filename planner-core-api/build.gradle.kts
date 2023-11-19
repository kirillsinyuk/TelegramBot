plugins {
    java
    id("com.google.protobuf") version "0.9.4"
}

group = "com.kvsinyuk"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly("com.google.protobuf:protobuf-java:3.25.0")
}

protobuf {
    protoc {
        if (osdetector.os == "osx") {
            artifact = "com.google.protobuf:protoc:3.14.0:osx-x86_64"
        } else {
            artifact = "com.google.protobuf:protoc:3.14.0"
        }
    }
}
