plugins {
    id("org.springframework.boot") version "2.4.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    java
}

group = "io.flpmartins88"
version = "1.0.0"

repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

dependencies {
    implementation(project(":commons"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.apache.kafka:kafka-streams")
    implementation("org.springframework.kafka:spring-kafka")

    implementation(group = "io.confluent", name = "kafka-streams-avro-serde", version = "6.1.1")
    implementation(group = "io.confluent", name = "kafka-avro-serializer", version = "6.1.1") {
        exclude(group = "org.slf4j")
        exclude(group = "log4j")
    }

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}