# Project graph-builder

This project provides utility classes to generate a network that can produce a json file to be used for testing in the [ripple-simulator fork](https://github.com/sebastiandagostino/ripple-simulator). 

## Prerequisites

Java JDK 8 and Maven are required to run the project.

## Compiling

As in any Maven project, it may be required to run

```
mvn clean install
```

## Running

After compilation with Maven, to run it in any console:

```
java -jar target/GraphBuilder-1.0-jar-with-dependencies.jar 54 3 500 500
```

The four parameters after the jar file are the following:

* _Graph Size_: must be a positive integer greater than _Clique Size_ 
* _Clique Size_: must be a positive integer greater than 1
* _Max Node Latency_: must be a positive integer
* _Max Link Latency_: must be a positive integer

There is another parameter that is optional:

* _Additional Nodes_: must be positive number to use as amount of new nodes in the improvement algorithm

Alternatively the application can be run with the following bash script: 

```
./gb.sh 54 3 500 500
```

This application creates two files (file1.json and file2.json) running the application with the same parameters except that for the second one it runs the improvement algorithm with 8% (a parameter in the bash script) of additional nodes. 

## Built With

* [Jackson](https://github.com/FasterXML/jackson) - Used to generate JSON streams
* [JGraphT](http://jgrapht.org/) - Utility framework for graph creation and clique search
* [Maven](https://maven.apache.org/) - Dependency Management

## Author

* **Sebastian D'Agostino** - *Initial work*
