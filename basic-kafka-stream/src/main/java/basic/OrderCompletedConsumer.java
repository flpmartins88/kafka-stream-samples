package basic;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class OrderCompletedConsumer {

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-001");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

        KafkaConsumer<String, SpecificRecord> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("order_completed"));

        while (true) {
            consumer.poll(Duration.ofSeconds(1)).forEach(
                    record -> System.out.printf(
                            "offset = %d, key = %s, value = %s%n",
                            record.offset(),
                            record.key(),
                            record.value()
                    )
            );
        }

    }

}
