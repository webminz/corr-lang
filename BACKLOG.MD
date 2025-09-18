# Project Status

Keeping track of the project status...

## Worklist

- [ ] Project structure
  - [ ] replace `mdegraphlib` sub-module project dependency with 
    maven/ivy-dependency hosted in a suitable artifact repository hosted
    (most likely GitHub packages).
  - [ ] remove `corr-lang-emf` altogether because 1. EMF is legacy 
  technology and 2. it can probably be replaced by an _XML tech space_.
  - [ ] rethink project layout for `corr-lang-gql` such that we get rid
  of the "in-tree" sub-module dependencies
  - [ ] same for `corr-lang-integrationtest` ...
- [ ] Core API
  - [ ] Stabilise _Tech Space Capabilities_ (`io.corrlang.techspaces`).
  - [ ] Recreate _capabilities_ API for the ProtocolBuffer implementation
  - [ ] Refactoring of the [`Schema`](./corr-lang-core/src/main/java/io/corrlang/domain/schemas/Schema.java) type API (-> use Caffeine Caches)
  - [ ] Fixing the `ComprSys` (construction) API and reactivate [`ComprehensiveSystemsTest`](./corr-lang-core/src/test/java/io/corrlang/systems/ComprehensiveSystemsTest.java)
  - [ ] Replace Spring with plain-Java `ServiceLoader` and fix startup sequence (formerly DIContainer => `io.corrlang.di`).
  - [ ] Better package layout for endpoint _marker_ interfaces (`io.corrlang.domain.diagrams`) => Most of them can become `record`s.
  - [ ] Rethink the `QualifiedName`/`ElementRef` classes.
  - [ ] Remove the legacy [`MessageArgument`](./corr-lang-core/src/main/java/io/corrlang/domain/MessageArgument.java),
  [`MessageContainer`](./corr-lang-core/src/main/java/io/corrlang/domain/MessageContainer.java), [`MessageType`](./corr-lang-core/src/main/java/io/corrlang/domain/MessageType.java).
- [ ] Base Plugins
  - [ ] PlantUML
    - [X] Single Schema drawing
    - [ ] ComprSys drawing
    - [ ] invoking plantuml as a process
  - [ ] JsonSchema
- [ ] Engine Services
  - [ ] Visitor to Domain Object implementations for the new Antlr Grammar
  - [ ] CLI arg parser


## Future Ideas

- Common ABI for the mdegraphlib that is more aligned with the AlgebraicJulia model.
