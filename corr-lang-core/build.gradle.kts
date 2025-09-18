import com.google.protobuf.gradle.*

plugins {
    id("java-library")
    id("corrlang.java-conventions")
    `java-test-fixtures`
    id("com.google.protobuf").version("0.9.5")

}

dependencies {
    api(project(":mdegraphlib"))
    api("javax.annotation:javax.annotation-api:1.3.2")
    api("org.springframework:spring-context:6.1.14")
    implementation("ch.qos.logback:logback-classic:1.5.11")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-toml:2.18.0")
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0") // TODO: really needed?

    runtimeOnly("io.grpc:grpc-netty-shaded:1.75.0")
    implementation("io.grpc:grpc-protobuf:1.75.0")
    implementation("io.grpc:grpc-stub:1.75.0")

    testImplementation(testFixtures(project(":mdegraphlib")))
    testFixturesCompileOnly("org.junit.jupiter:junit-jupiter:5.8.1")
    testFixturesCompileOnly(testFixtures(project(":mdegraphlib")))
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.5"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.75.0"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                id("grpc") {}
            }
        }
    }
}