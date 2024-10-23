plugins {
    java
    application
    id("corrlang.java-conventions")
    id("antlr")
}

tasks.generateGrammarSource {
    maxHeapSize = "64m"
    arguments = arguments +  listOf("-package", "io.corrlang.parser")
    outputDirectory = File("src/generated/io/corrlang/parser")
}

dependencies {
    implementation(project(":corr-lang-core"))
    implementation("org.antlr:antlr4-runtime:4.8")
    antlr("org.antlr:antlr4:4.8")
    implementation("io.javalin:javalin:6.3.0")
    testImplementation(testFixtures(project(":mdegraphlib")))
    testImplementation(testFixtures(project(":corr-lang-core")))
    testImplementation(("ch.qos.logback:logback-classic:1.5.11"))
}


application {
    mainClass = "io.corrlang.engine.runner.ConsoleRun"
    applicationName = "corrlang"
}

tasks.clean {
    delete("src/generated")
}


tasks.compileJava {
    dependsOn(tasks.generateGrammarSource)
}

sourceSets {
    main {
        antlr.srcDirs("src/main/antlr")
        java.srcDir("src/main/java")
        java.srcDir("src/generated")
        resources.srcDir("src/main/resources")
    }
    test {
        java.srcDir("src/test/java")
        resources.srcDir("src/test/resources")
    }
}

//tasks.test {
//    filter {
//        includeTestsMatching("io.corrlang.engine.CorrLangEngineSuite*")
//    }
//}

tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

//distributions {
//    main {
//        contents {
//            into("plugins") {
//                from("${buildDir}/plugins")
//            }
//            into("pluginDependencies") {
//                from("${buildDir}/pluginDeps")
//            }
//        }
//    }
//}

//tasks.startScripts {
    //classpath = classpath + files("../plugins")
    //classpath = classpath +  files("../pluginDependencies")
//    applicationName = "corrlang"
    // Apparently the gradle plugin is a bit "stupid" ...
    // https://discuss.gradle.org/t/classpath-in-application-plugin-is-building-always-relative-to-app-home-lib-directory/2012
//    doLast {
//        val windowsScriptFile = file(getWindowsScript())
//        val unixScriptFile = file(getUnixScript())
//        windowsScriptFile. = windowsScriptFile.text.replaceAll("%APP_HOME%\\\\lib\\\\plugins", "%APP_HOME%\\plugins\\*")
//        windowsScriptFile.text = windowsScriptFile.text.replaceAll("%APP_HOME%\\\\lib\\\\pluginDependencies", "%APP_HOME%\\pluginDependencies\\*")
//        unixScriptFile.text = unixScriptFile.text.replace("$APP_HOME/lib/plugins", "$APP_HOME/plugins/*")
//        unixScriptFile.text = unixScriptFile.text.replace("$APP_HOME/lib/pluginDependencies", "$APP_HOME/pluginDependencies/*")
//    }
//}


