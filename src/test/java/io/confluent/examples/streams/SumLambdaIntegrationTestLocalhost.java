package io.confluent.examples.streams;

import io.confluent.examples.streams.kafka.EmbeddedSingleNodeKafkaCluster;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.integration.utils.IntegrationTestUtils;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.test.TestUtils;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertEquals;

/**
 * Created by Bjørn Hjelle, Acando on 12.03.2018.
 */
public class SumLambdaIntegrationTestLocalhost {

    private static String inputTopic = "inputTopic";
    private static String outputTopic = "outputTopic";


/*
    @ClassRule
    public static final EmbeddedSingleNodeKafkaCluster CLUSTER = new EmbeddedSingleNodeKafkaCluster();

    @BeforeClass
    public static void startKafkaCluster() throws Exception {
        CLUSTER.createTopic(inputTopic);
        CLUSTER.createTopic(outputTopic);
    }
*/


    @Test
    public void shouldSumEvenNumbers() throws Exception {
        List<Integer> inputValues = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> expectedValues = Collections.singletonList(30);

        System.out.println(inputValues);

        //
        // Step 1: Configure and start the processor topology.
        //
        StreamsBuilder builder = new StreamsBuilder();

        Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "app-id");
        streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, "client-id");
       streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "http://localhost:9092");
//        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, CLUSTER.bootstrapServers());
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Integer().getClass().getName());
        // The commit interval for flushing records to state stores and downstream must be lower than
        // this integration test's timeout (30 secs) to ensure we observe the expected processing results.
//        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10 * 1000);
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100);
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        // Use a temporary directory for storing state, which will be automatically removed after the test.
        streamsConfiguration.put(StreamsConfig.STATE_DIR_CONFIG, TestUtils.tempDirectory().getAbsolutePath());

        KStream<Integer, Integer> input = builder.stream(inputTopic);
        input.foreach((k, v) -> System.out.println("input value: " + v));

        KGroupedStream<Integer, Integer> grouped = input
                .filter((k, v) -> v % 2 == 0)
                .selectKey((k, v) -> 1)
                // no need to specify explicit serdes because the resulting key and value types match our default serde settings
                .groupByKey();

        KTable<Integer, Integer> sumOfOddNumbers = grouped
                .reduce((v1, v2) -> v1 + v2);/*
        sumOfOddNumbers.toStream().foreach((k, v) -> System.out.println("key: " + k));
        sumOfOddNumbers.toStream().to(outputTopic);
*/
        sumOfOddNumbers.toStream().to(outputTopic);

        KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);
        streams.start();
        //
        // Step 2: Produce some input data to the input topic.
        //
        Properties producerConfig = new Properties();
//        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, CLUSTER.bootstrapServers());
        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://localhost:9092");
        producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");
        producerConfig.put(ProducerConfig.RETRIES_CONFIG, 0);
        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        IntegrationTestUtils.produceValuesSynchronously(inputTopic, inputValues, producerConfig, Time.SYSTEM);

        //
        // Step 3: Verify the application's output data.
        //
        Properties consumerConfig = new Properties();
     //   consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, CLUSTER.bootstrapServers());
        consumerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://localhost:9092");
        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, "sum-lambda-integration-test-standard-consumer");
        consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        consumerConfig.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);

        Consumer<Object, String> consumer = createConsumer(consumerConfig);
        consumer.subscribe(Arrays.asList(outputTopic));
        List<String> actualValues = new ArrayList<>();
        for  (int i=0;i< 4; i++) {
            try {
                System.out.println("poll");

                ConsumerRecords<Object, String> records = consumer.poll(200);
                System.out.println(records.count());
                for (ConsumerRecord<Object, String> record : records) {
                    System.out.println(String.valueOf(record.value()));
                    actualValues.add(record.value());
                }

            } catch (SerializationException ex) {

                System.out.println(ex.getMessage());
            }
        }


/*
        List<String> actualValues = IntegrationTestUtils.waitUntilMinValuesRecordsReceived(consumerConfig,
                outputTopic, expectedValues.size());

*/

        streams.close();

        System.out.println("actual:" + actualValues);
        System.out.println("expected:" + expectedValues);
        assertThat(actualValues).isEqualTo(expectedValues);
    }


    private static <K, V> KafkaConsumer<K, V> createConsumer(Properties consumerConfig) {
        Properties filtered = new Properties();
        filtered.putAll(consumerConfig);
        filtered.setProperty("auto.offset.reset", "earliest");
        filtered.setProperty("enable.auto.commit", "true");
        return new KafkaConsumer(filtered);
    }

}
