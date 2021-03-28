plugins {
    java
    id("org.springframework.boot") version "2.4.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "io.flpmartins88"
version = "1.0.0"

repositories {
    mavenCentral()
    maven {
        url = uri("https://packages.confluent.io/maven/")
    }
}

extra["springCloudVersion"] = "2020.0.1"

dependencies {
    implementation(project(":commons"))

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.apache.kafka:kafka-streams")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.cloud:spring-cloud-stream")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka-streams")

    implementation(group = "io.confluent", name = "kafka-streams-avro-serde", version = "6.1.0")
    implementation(group = "io.confluent", name = "kafka-avro-serializer", version = "6.1.0") {
        exclude(group = "org.slf4j")
        exclude(group = "log4j")
    }

    implementation("org.apache.avro:avro:1.10.1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.kafka:spring-kafka-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}
