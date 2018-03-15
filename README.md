# Confluent streams examples

This repository contains some of the Confluent streams examples, with gradle instead of maven as build automation tool.

The examples are copied from here: 
https://github.com/confluentinc/kafka-streams-examples

## Clone and build

    $ git clone ....
    $ cd ...
    $ gracle build -x test

## To generate Java classes from Avro schemas

    $ gradle generateAvro

## Run tests
When first running the tests a bunch of warnings were displayed. To suppress the warnings, configuration has been added to src/test/resources/log4j.properties.

    $ gradle test --rerun-tasks
    
## Run a single test

    $ gradle test --tests=io.confluent.examples.streams.SumLambdaIntegrationTest --rerun-tasks

    