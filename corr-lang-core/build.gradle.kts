plugins {
    id("java-library")
    id("corrlang.java-conventions")
    `java-test-fixtures`

}

dependencies {
    api(project(":mdegraphlib"))
    api("javax.annotation:javax.annotation-api:1.3.2")
    api("org.springframework:spring-context:6.1.14")
    implementation("ch.qos.logback:logback-classic:1.5.11")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-toml:2.18.0")

    testImplementation(testFixtures(project(":mdegraphlib")))
    testFixturesCompileOnly("org.junit.jupiter:junit-jupiter:5.8.1")
    testFixturesCompileOnly(testFixtures(project(":mdegraphlib")))
}

