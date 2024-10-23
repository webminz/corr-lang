

//task buildCorrlang(type: Copy) {
//	group = "corrlang"
//	description = "Builds the whole project and produces an executable Jarfile"
//
//	dependsOn(updateVersionInfo)
//	dependsOn(collectCorrLangPluginDependencies)
//	dependsOn(buildCorrLangPlugins)
//	dependsOn(tasks.getByPath(':corr-lang-engine:distZip'))
//	dependsOn(tasks.getByPath(':corr-lang-engine:distTar'))
//
//	from("${project(":corr-lang-engine").buildDir}/distributions")
//	into("${project.rootDir}/release")
//}
//
//tasks.getByPath(':corr-lang-engine:distZip').mustRunAfter(collectCorrLangPluginDependencies)
//tasks.getByPath(':corr-lang-engine:distZip').mustRunAfter(buildCorrLangPlugins)
//tasks.getByPath(':corr-lang-engine:distTar').mustRunAfter(collectCorrLangPluginDependencies)
//tasks.getByPath(':corr-lang-engine:distTar').mustRunAfter(buildCorrLangPlugins)

