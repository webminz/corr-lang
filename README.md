# CorrLang
CorrLang is a tool/library that helps you with several model and system integration tasks.
It is based on a DSL with the same name.
In this DSL you specify structural relations between schemas of different endpoints. 

## Installation

To run CorrLang on your own machine you have to check it out from here and build it on your machine.

You have to have `java` (>= 1.8) and `git` installed!

First check out with `git clone`.
Please note that this project has _submodules_ ([You can learn more about submodules here](https://git-scm.com/book/en/v2/Git-Tools-Submodules)).

Thus, you will have to call
```
git submodule init
```
for each submodule.

And, you have to pull changes with 
```
git pull --recurse-submodules
```
To get all changes recursively for all submodules!

After downloading the project, you build the whole thing with
```
./gradlew buildCorrlang
```

You will then see the message
```
CorrLang successfully built: file:/...
```

which will point you to the directory where you find the freshly built Jar-file.
This files packages all dependencies into one such that you are immediately ready to go!

## Running

Copy the `corrlang.jar` where you want to have it, optionally make a SHELL-script for it and it to your $PATH.
Running 
```
java -jar corrlang.jar
```
for the first time will give you an idea how to proceed.


To get some inspiration for writing you first _CorrSpec_ file, you might want to have a look at an example featuring the popular [Families2Persons](https://wiki.eclipse.org/ATL/Tutorials_-_Create_a_simple_ATL_transformation) use case: 
```
endpoint Families {
    type FILE
    at models/Families.families
    technology ECORE
    schema metamodels/Families.ecore
}

endpoint Persons {
    type FILE
    at models/Persons.persons
    technology ECORE
    schema metamodels/Persons.ecore
}


correspondence Families2Persons (Families,Persons) {

sync (Families.FamilyMember as fm , Persons.Male as m) as syncMale
      when (fm.name ++ " " ++ fm.fatherInverse.name == m.name ||  fm.name ++ " " ++ fm.sonsInverse.name == m.name );

sync (Families.FamilyMember as fm, Persons.Female as f) as syncFemale
	  when (fm.name ++ " " ++ fm.motherInverse.name == f.name ||  fm.name ++ " " ++ fm.daughtersInverse.name == f.name );

}

goal Plot {
    correspondence Families2Persons
    action SCHEMA
	technology PNG
	target FILE {
		at output/familiesAndPersons.png
	}
}
```

You might also want to have a look into my publications related to the topic:
* [Towards Multiple Model Synchronization with Comprehensive Systems
  ](https://link.springer.com/chapter/10.1007%2F978-3-030-45234-6_17)
    
* [GraphQL Federation: A Model-Based Approach
  ](http://www.jot.fm/contents/issue_2020_02/article18.html)
  
Or just take [Contact](https://www.hvl.no/person/?user=Patrick.Stunkel)
