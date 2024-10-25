rootProject.name = "corrlang"
include("mdegraphlib") // Essential base library that CorrLang depends on
include("corr-lang-core") // Central CorrLang components, also required for plugin development
include("corr-lang-base-plugins") // Plugins always shipped with CorrLang
include("corr-lang-engine") // The CorrLang Application, builds on the core project
//include 'corr-lang-gql' // Comment/Uncomment whether you want to build the official CorrLang integration with GraphQL
//include 'corr-lang-emf' // Comment/Uncomment whether you want to build the official CorrLang integration with Eclipse Modeling Framework
//include 'corr-lang-integrationtest' // Comment/Uncomment whether you want to run/develop CorrLang system tests
