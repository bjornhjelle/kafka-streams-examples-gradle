package no.test;

import io.confluent.examples.streams.avro.Song;
import io.confluent.examples.streams.kafka.EmbeddedSingleNodeKafkaCluster;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Bjørn Hjelle, Acando on 14.03.2018.
 */
public class SerializationExceptionExample {

    private static String outputTopic = "songs";
    final static Logger logger = Logger.getLogger(SerializationExceptionExample.class);


    @ClassRule
    public static final EmbeddedSingleNodeKafkaCluster CLUSTER = new EmbeddedSingleNodeKafkaCluster();

    @BeforeClass
    public static void startKafkaCluster() throws Exception {
        CLUSTER.createTopic(outputTopic);
    }


    @Test
    public void shouldHandleSerializationException() throws Exception {



        // create a producer
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, CLUSTER.bootstrapServers());
        producerProperties.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, CLUSTER.schemaRegistryUrl());
        producerProperties.put(ProducerConfig.ACKS_CONFIG, "all");
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);

        Producer<Long, Song> songProducer = new KafkaProducer<Long, Song>(producerProperties);

        // create and publish a Song event to a topic
        Song song_1 = Song.newBuilder()
                .setId(1L).setAlbum("N/A").setArtist("Higasakite").setGenre("indie rock").setName("5 Million Miles")
                .build();
        ProducerRecord<Long, Song> producerRecord = new ProducerRecord<Long, Song>(outputTopic, song_1.getId(), song_1);
        songProducer.send(producerRecord).get();

        // create and publish another Song event to a topic, but with a string as the key
        Producer<String, Song> anotherSongProducer = new KafkaProducer<String, Song>(producerProperties, new StringSerializer(), null);
        ProducerRecord<String, Song> producerRecordString = new ProducerRecord<String, Song>(outputTopic, song_1.getName(), song_1);
        anotherSongProducer.send(producerRecordString).get();

        // publish a third song event
        songProducer.send(producerRecord).get();


        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, CLUSTER.bootstrapServers());
        consumerProperties.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, CLUSTER.schemaRegistryUrl());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        consumerProperties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 100);
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        consumerProperties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);


        KafkaConsumer<Long, Song> kafkaConsumer = new KafkaConsumer<Long, Song>(consumerProperties);
        kafkaConsumer.subscribe(Arrays.asList(outputTopic));

        List<Song> songs = new ArrayList<>();
        for  (int i=0;i<4; i++) {
     //   while  (true) {
            try {
                logger.info("poll");
                ConsumerRecords<Long, Song> records = kafkaConsumer.poll(200);
                for (ConsumerRecord<Long, Song> record : records) {
                    songs.add((Song)record.value());
                }
            } catch (SerializationException ex) {
                logger.error(ex.getMessage(), ex);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                logger.error(ex.getMessage(), ex);
            }
        }

        assertThat(songs.size()).isEqualTo(2);
        assertThat(songs.get(0)).isEqualTo(song_1);
        assertThat(songs.get(1)).isEqualTo(song_1);
    }


}
