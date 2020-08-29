# Word Count

Project to count words in covid19 tweets dataset

## Getting Started

To run this project you need to create the docker image locally with [build command](#build), after the docker image created just run the [command](#running) to get the word count result.

## Requirements

- [Docker](https://docs.docker.com/engine/install/ubuntu/) >= 19.03.8

## Build

To build container image execute:
```
make build
```

## Running

##### Parameters: 

**THRESHOLD** - Number of word characters in result. Default = 7

**LINES** - Number of result lines. Default = 100

##### Example:
````bash
make run THRESHOLD=5 LINES=50
````

## Running tests
To run tests execute: 

```
make test
```

## Build with

- [SBT](https://www.scala-sbt.org/) build tool for projects in Scala
- [Apache Spark](https://spark.apache.org/) for large-scale data processing





