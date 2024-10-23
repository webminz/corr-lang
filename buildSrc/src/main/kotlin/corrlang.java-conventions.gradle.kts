plugins {
    id("java")
}

group = "io.corrlang"
version = "1.0-snapshot"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("com.google.guava:guava:33.3.1-jre")
    implementation("org.slf4j:slf4j-api:2.0.16")
    testCompileOnly("junit:junit:4.13")

}



tasks.named<Test>("test") {
    useJUnitPlatform()
}

