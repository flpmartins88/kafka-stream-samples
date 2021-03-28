plugins {
    java
    id("com.github.davidmc24.gradle.plugin.avro") version "1.0.0"
}

avro {
    isCreateSetters.set(false)
    fieldVisibility.set("PRIVATE")
    outputCharacterEncoding.set("UTF-8")
}

tasks.named("compileJava") {
    dependsOn("generateAvroJava")
}

sourceSets {
    main {
        java.srcDir("build/generated-main-avro-java")
    }
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
    implementation(group="io.confluent", name="kafka-avro-serializer", version="6.1.1")
}

val instrumentedJars: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
    attributes {
        attribute(Category.CATEGORY_ATTRIBUTE, namedAttribute(Category.LIBRARY))
        attribute(Usage.USAGE_ATTRIBUTE, namedAttribute(Usage.JAVA_RUNTIME))
        attribute(Bundling.BUNDLING_ATTRIBUTE, namedAttribute(Bundling.EXTERNAL))
        attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, JavaVersion.current().majorVersion.toInt())
        attribute(LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE, namedAttribute("instrumented-jar"))
    }
}

inline fun <reified T: Named> Project.namedAttribute(value: String) = objects.named(T::class.java, value)