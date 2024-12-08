# Reactive pulsar client with Spring-boot 3.4.0


This project implements the Java LookupData producer implemented in Java from https://rockthejvm.com/articles/stateful-streams-with-apache-pulsar-and-apache-flink/ I used the latest springboot 3.4.0 and Apache Pulsar reactive client 4.0.1 The data is in the resources folder and using the same data. Using the springboot Resource loader, spring-boot-starter-pulsar-reactive, spring-boot-starter-webflux and Apache common-csv to read the files from the folder (Check the CsvService class) and the springboot pulsar client to produce data to pulsar. 

User the pulsar-docker-compose.yml file in the root folder to start your pulsar in standalone mode in the terminal ```docker-compose -f pulsar-docker-compose.yml up -d``` and you can use the setup script in the root folder to set docker u.p In the terminal run ```./setup.sh```
and all you have left is to run ```mvn spring-boot:run``` as this a maven springboot project.
