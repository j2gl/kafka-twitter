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

## Kafka commands
```sh
# List topics
kafka-topics --bootstrap-server localhost:9092 --list

# Create topic
kafka-topics --bootstrap-server localhost:9092 --create --topic twitter_tweets --partitions 6 --replication-factor 1

# Show topic information
kafka-topics --bootstrap-server localhost:9092 --topic twitter_tweets --describe

# Create consumer
kafka-console-consumer --bootstrap-server localhost:9092 --topic twitter_tweets --from-beginning

# Delete topic 
kafka-topics --bootstrap-server localhost:9092 --topic twitter_tweets --delete
```
