package com.github.j2gl.elasticsearch;

import com.google.gson.JsonParser;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ElasticSearchConsumer {

    public static RestHighLevelClient createClient() {
        String hostname = "kafka-twitter-5225177207.eu-central-1.bonsaisearch.net";
        String username = "NTCnRSxbK8";
        String password = "";

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));

        RestClientBuilder builder = RestClient.builder(new HttpHost(hostname, 443, "https"))
                .setHttpClientConfigCallback(
                    httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                );

        return new RestHighLevelClient(builder);
    }

    public static KafkaConsumer<String, String> createConsumer(String topic) {
        final String bootstrapServers = "127.0.0.1:9092";
        final String groupId = "kafka-demo-elasticsearch";

        final Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "100");

        // Create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(topic));
        return consumer;
    }

    public static void main(String[] args) throws IOException {
        Logger logger = LoggerFactory.getLogger(ElasticSearchConsumer.class.getName());
        RestHighLevelClient client = createClient();
        KafkaConsumer<String, String> consumer = createConsumer("twitter_tweets");

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            int recordCount = records.count();
            logger.info("Received size={} records", recordCount);

            BulkRequest bulkRequest = new BulkRequest();

            for (ConsumerRecord<String, String> record : records) {
                // Strategy 1: generate kafka generic ID.
                // String id = record.topic() + "_" + record.partition() + "_" + record.offset();

                final String jsonString = record.value();
                // Strategy 2
                String tweetId = extractIdFromTweet(jsonString);

                IndexRequest indexRequest = new IndexRequest("twitter")
                        .id(tweetId)
                        .source(jsonString, XContentType.JSON);
                bulkRequest.add(indexRequest);
            }

            if (recordCount > 0) {
                BulkResponse bulkItemResponses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
                for (BulkItemResponse bulkItemResponse : bulkItemResponses.getItems()) {
                    String tweetId = bulkItemResponse.getId();
                    logger.info("Index={}, tweetId={}", bulkItemResponse.getIndex(), tweetId);
                }

                logger.debug("Committing offsets.");
                consumer.commitSync();
                logger.info("Offsets committed for size={} records", records.count());

                try {
                    Thread.sleep(20_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
//        client.close();
    }

    private static String extractIdFromTweet(String jsonTweet) {
        return JsonParser.parseString(jsonTweet)
                .getAsJsonObject()
                .get("data")
                .getAsJsonObject()
                .get("id")
                .getAsString();
    }


}
