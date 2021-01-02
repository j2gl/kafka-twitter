# README

## Requisites
* Java 
* Kafka
* Access to Twitter API

## Install Kafka
```
brew install kafka
```

## Start kafka

```sh
# Start Zookeeper
cd kafka_2.13-2.7.0
zookeeper-server-start config/zookeeper.properties

# Start Kafka
kafka-server-start config/server.properties
```
