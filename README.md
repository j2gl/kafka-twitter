# README

This is a POC from the course I'm taking for [Kafka in udemy](https://www.udemy.com/share/1013hcA0oTdFxVQHo=/).

It has two main programs:
* **TwitterStream**: Connects to Twitter API and then sends tweet messages to `twitter_tweets` topic.
* **ElasticSearchConsumer**: Which consumes messages from `twitter_tweets` topic, and pushes them to Kafka.

Under api-test, you can find curls to test the [twitter api](api-test/elasticsearch) and [elasticsearch api](api-test/twitter).


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
kafka-topics --bootstrap-server localhost:9092 --describe --topic twitter_tweets 

# Create consumer
kafka-console-consumer --bootstrap-server localhost:9092 --topic twitter_tweets --from-beginning

# Delete topic 
kafka-topics --bootstrap-server localhost:9092 --topic twitter_tweets --delete

# Check consumer group
kafka-consumer-groups --bootstrap-server localhost:9092 --group kafka-demo-elasticsearch --describe

# Reset offsets
kafka-consumer-groups --bootstrap-server localhost:9092 --group kafka-demo-elasticsearch --reset-offsets --topic twitter_tweets --execute --to-earliest

```
