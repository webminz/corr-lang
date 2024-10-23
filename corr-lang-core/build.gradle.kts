plugins {
    id("java-library")
    id("corrlang.java-conventions")
    `java-test-fixtures`

}

dependencies {
    api(project(":mdegraphlib"))
    api("javax.annotation:javax.annotation-api:1.3.2")
    implementation("net.sourceforge.plantuml:plantuml:1.2021.4")
    api("org.springframework:spring-context:6.1.14")
    implementation("ch.qos.reload4j:reload4j:1.2.25")
    testImplementation(testFixtures(project(":mdegraphlib")))
    testFixturesCompileOnly("org.junit.jupiter:junit-jupiter:5.8.1")
    testFixturesCompileOnly(testFixtures(project(":mdegraphlib")))
}

