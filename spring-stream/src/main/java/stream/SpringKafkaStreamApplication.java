package stream;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

import java.util.HashMap;
import java.util.Map;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.flpmartins88.streams.events.CustomerEvent;
import io.flpmartins88.streams.events.OrderCompletedEvent;
import io.flpmartins88.streams.events.OrderEvent;
import io.flpmartins88.streams.events.PaymentEvent;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

@SpringBootApplication
public class SpringKafkaStreamApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringKafkaStreamApplication.class, args);
    }

}

@Configuration
@EnableKafka
@EnableKafkaStreams
class KafkaConfig {

    @Value("${spring.kafka.streams.properties.schema.registry.url}")
    private String schemaRegistryUrl;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs(KafkaProperties properties) {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, properties.getStreams().getApplicationId());
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getStreams().getBootstrapServers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        props.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class.getName());
        props.put(SCHEMA_REGISTRY_URL_CONFIG, properties.getStreams().getProperties().get(SCHEMA_REGISTRY_URL_CONFIG));

        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    KStream<String, OrderCompletedEvent> orderCompletedProcessor(StreamsBuilder builder) {

        KTable<String, CustomerEvent> customerTable = builder.table("customer", Consumed.with(
                getPrimitiveAvroSerde(true),
                getSpecificAvroSerde()
        ));

        KTable<String, OrderEvent> orderTable = builder.table("order", Consumed.with(
                getPrimitiveAvroSerde(true),
                getSpecificAvroSerde()
        ));

        KTable<String, PaymentEvent> paymentTable = builder.table("payment", Consumed.with(
                getPrimitiveAvroSerde(true),
                getSpecificAvroSerde()
        ));

        var orderCompletedStream = paymentTable.join(
                orderTable,
                c -> c.getSource().getId(),
                new OrderPaymentJoiner()
        ).join(
                customerTable,
                orderCompleted -> orderCompleted.getCustomer().getId(),
                new OrderCustomerJoiner()
        ).toStream();

        orderCompletedStream.to("order_completed", Produced.with(
                getPrimitiveAvroSerde(true),
                getSpecificAvroSerde()
        ));

        orderCompletedStream.print(Printed.toSysOut());

        return orderCompletedStream;
    }


    private <T> Serde<T> getPrimitiveAvroSerde(boolean isKey) {
        final KafkaAvroDeserializer deserializer = new KafkaAvroDeserializer();
        final KafkaAvroSerializer serializer = new KafkaAvroSerializer();
        final Map<String, String> config = new HashMap<>();
        config.put(SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
        deserializer.configure(config, isKey);
        serializer.configure(config, isKey);
        return (Serde<T>) Serdes.serdeFrom(serializer, deserializer);
    }

    private <T extends SpecificRecord> SpecificAvroSerde<T> getSpecificAvroSerde() {
        final SpecificAvroSerde<T> specificAvroSerde = new SpecificAvroSerde<>();

        final HashMap<String, String> serdeConfig = new HashMap<>();
        serdeConfig.put(SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

        specificAvroSerde.configure(serdeConfig, false);
        return specificAvroSerde;
    }

}

class OrderPaymentJoiner implements ValueJoiner<PaymentEvent, OrderEvent, OrderCompletedEvent> {
    @Override
    public OrderCompletedEvent apply(PaymentEvent payment, OrderEvent order) {
        var customer = CustomerEvent.newBuilder()
                .setId(order.getCustomer())
                .setName(order.getCustomer()) // Vazio pq é not null
                .build();

        return OrderCompletedEvent.newBuilder()
                .setId(order.getId())
                .setItems(order.getItems())
                .setPaymentStatus(payment.getResult())
                .setCustomer(customer)
                .build();
    }
}

class OrderCustomerJoiner implements ValueJoiner<OrderCompletedEvent, CustomerEvent, OrderCompletedEvent> {
    @Override
    public OrderCompletedEvent apply(OrderCompletedEvent order, CustomerEvent customer) {
        return OrderCompletedEvent.newBuilder()
                .setId(order.getId())
                .setItems(order.getItems())
                .setPaymentStatus(order.getPaymentStatus())
                .setCustomer(customer)
                .build();
    }
}