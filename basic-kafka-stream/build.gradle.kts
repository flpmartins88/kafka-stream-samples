plugins {
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

    implementation(group = "org.apache.kafka", name = "kafka-streams", version = "2.7.0")

    implementation(group = "io.confluent", name = "kafka-streams-avro-serde", version = "6.1.1")
    implementation(group = "io.confluent", name = "kafka-avro-serializer", version = "6.1.1") {
        exclude(group = "org.slf4j")
        exclude(group = "log4j")
    }
    
}
