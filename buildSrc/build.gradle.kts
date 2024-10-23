plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}




//task updateVersionInfo {
//	group = "corrlang"
//	description "Updates the VERSION.INFO file in the CorrLang engine project, which the final application uses to display meta-information"
//	doLast {
//		def isRelease = findProperty('release') ?: 'false'
//		def gitBranch = "Unknown branch"
//		try {
//			def workingDir = new File("${project.projectDir}/corr-lang-engine")
//			def result = 'git rev-parse HEAD'.execute(null, workingDir)
//			result.waitFor()
//			if (result.exitValue() == 0) {
//				gitBranch = result.text.trim()
//			}
//		} catch (e) {
//			e.printStackTrace()
//		}
//		Properties props = new Properties()
//		File propsFile = new File("${project.projectDir}/corr-lang-engine/src/main/resources/BUILD.INFO")
//		if (propsFile.exists()) {
//			props.load(propsFile.newDataInputStream())
//		}
//		def buildNo = 1 + Integer.parseInt(props.getProperty("BUILD_NO", "0"))
//		props.setProperty("BUILD_NO", buildNo.toString())
//		props.setProperty("COMMIT",gitBranch)
//		props.setProperty("BUILD_DATE", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
//		if (Boolean.parseBoolean(isRelease)) {
//			props.setProperty("BUILD_TYPE", "RELEASE")
//		} else {
//			props.setProperty("BUILD_TYPE", "DEVELOPER")
//		}
//		props.store(propsFile.newDataOutputStream(),null)
//	}
//}
//
//task collectCorrLangPluginDependencies(type: Copy) {
//	group = "corrlang"
//	description = "Exports all Plugin Dependencies"
//	def dependents = subprojects.findAll {it.plugins.hasPlugin("java") }
//			.findAll { it.name != "corr-lang-core" && it.name != "corr-lang-engine" && it.name != "corr-lang-integrationtest" && it.name != "mdegraphlib"}
//	dependents.each { it.getTasks().findAll {it.name == "exportDependencies"}.each {dependsOn(it)} }
//	dependents.each {from("${it.buildDir}/extlibs") { include('*.jar')}}
//	into("${ project(":corr-lang-engine").buildDir }/pluginDeps")
//}
//
//
//task buildCorrLangPlugins(type: Copy) {
//	group = "corrlang"
//	description = "Builds all CorrLang Plugins that are contained in the project"
//	def dependents = subprojects.findAll { it.plugins.hasPlugin("java") }.findAll { it.name != "corr-lang-core" && it.name != "corr-lang-engine" && it.name != "corr-lang-integrationtest" && it.name != "mdegraphlib"}
//	dependents.each { dependsOn(it.jar) }
//	dependents.each {from("${it.buildDir}/libs") { include('*.jar')}}
//	into("${ project(":corr-lang-engine").buildDir }/plugins")
//}
//
//