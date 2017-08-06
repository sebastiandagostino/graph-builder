#!/usr/bin/env bash

java -jar target/GraphBuilder-1.0-jar-with-dependencies.jar $1 $2 $3 $4 >file1.json

java -jar target/GraphBuilder-1.0-jar-with-dependencies.jar $1 $2 $3 $4 1 >file2.json
