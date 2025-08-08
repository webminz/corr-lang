plugins {
    `java-library`
    id("corrlang.java-conventions")
}

dependencies {
    implementation(project(":corr-lang-core"))
    implementation("net.sourceforge.plantuml:plantuml:8059")
    testImplementation(testFixtures(project(":mdegraphlib")))
    testImplementation(testFixtures(project(":corr-lang-core")))
}

