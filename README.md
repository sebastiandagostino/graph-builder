# Project graph-builder

This project provides utility classes to generate a network that can produce a json file to be used for testing in the ripple-simulator fork. 

## Prerequisites

Java JDK 8 and Maven are required to run the project.

## Compiling

As in any Maven project, it may be required to run

```
mvn clean install
```

For the time being it produces nothing of interest, since it prints the output in pseudo-tests that are used in the ripple-simulator fork.

## Built With

* [Jackson](https://github.com/FasterXML/jackson) - Used to generate JSON streams
* [JGraphT](http://jgrapht.org/) - Utility framework for graph creation
* [Maven](https://maven.apache.org/) - Dependency Management

## Author

* **Sebastian D'Agostino** - *Initial work*
