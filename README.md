# Project graph-builder

This project provides utility classes to generate a network that can produce a json file to be used for testing in the [ripple-simulator fork](https://github.com/sebastiandagostino/ripple-simulator). 

## Prerequisites

Java JDK 8 and Maven are required to run the project.

## Compiling

As in any Maven project, it may be required to run

```
mvn clean install
```

After compilation with Maven, to run it in any console:

```
java -jar target/GraphBuilder-1.0-jar-with-dependencies.jar 54 3 6 500 500
```

The five parameters after the jar file are the following:

* _Graph Size_: must be a positive integer greater than _Clique Size_ 
* _Clique Size_: must be a positive integer greater than 1
* _Outbound Links Per Clique_: must be a positive integer greater than _Clique Size_
* _Max Node Latency_: must be a positive integer
* _Max Link Latency_: must be a positive integer

## Built With

* [Jackson](https://github.com/FasterXML/jackson) - Used to generate JSON streams
* [JGraphT](http://jgrapht.org/) - Utility framework for graph creation and clique search
* [Maven](https://maven.apache.org/) - Dependency Management

## Author

* **Sebastian D'Agostino** - *Initial work*
