#!/usr/bin/env bash

percentage=8

base=100

java -Xmx4096m -Xms256m -jar target/GraphBuilder-1.0-jar-with-dependencies.jar $1 $2 $3 $4 >file1.json

java -Xmx4096m -Xms256m -jar target/GraphBuilder-1.0-jar-with-dependencies.jar $1 $2 $3 $4 $(($1 * $percentage / $base)) >file2.json
