---
layout: page
title: Installation
permalink: install/
nav_order: 3
---

# _CorrLang_ installation

Pre-built _CorrLang_ bytecode binaries can be downloaded directly from GitHub:

[Download .zip](https://github.com/webminz/corr-lang/releases/download/v0.9/corrlang-0.9.zip)

The layout of the archive follows the convention for java applications.
Inside the `/bin` folder you will find the start scripts to run CorrLang on UNIX and Windows platforms.

## Compiling _CorrLang_ from source

Of course, you can compile _CorrLang_ yorself. 
Simple check out the main repository:
```
https://github.com/webminz/corr-lang.git
```
Since, the project is distributed over several submodules, on the first checkout you will have to navigate into the subfolders:
- mdegraphlib
- corr-lang-emf
- corr-lang-gql
and call
```
git submodule init
```
To download the respective repository code.

Finally, you can build CorrLang by calling
```
./gradlew buildCorrlang
```
in the repository root.
