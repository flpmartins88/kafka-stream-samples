package basic;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

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
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class Application {
    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-worker"); // <- nome que será usado para o kafka distribuir
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        props.put(SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        final StreamsBuilder builder = new StreamsBuilder();

        Serde<String> stringSerde = getPrimitiveAvroSerde(props, true);
        Serde<OrderEvent> orderEventSerde = getSpecificAvroSerde(props);
        Serde<CustomerEvent> customerEventSerde = getSpecificAvroSerde(props);
        Serde<PaymentEvent> paymentEventSerde = getSpecificAvroSerde(props);
        Serde<OrderCompletedEvent> orderCompletedEventSerde = getSpecificAvroSerde(props);

        KTable<String, OrderEvent> orderTable = builder.table(
                "order", Consumed.with(stringSerde, orderEventSerde)
        );

        KTable<String, CustomerEvent> customerTable = builder.table(
                "customer", Consumed.with(stringSerde, customerEventSerde)
        );

        KTable<String, PaymentEvent> paymentTable = builder.table(
                "payment", Consumed.with(stringSerde, paymentEventSerde)
        );

        paymentTable.join(
                orderTable,
                c -> c.getSource().getId(),
                new OrderPaymentJoiner()
        ).join(
                customerTable,
                orderCompleted -> orderCompleted.getCustomer().getId(),
                new OrderCustomerJoiner()
        ).toStream().to("order_completed", Produced.with(stringSerde, orderCompletedEventSerde));

        Topology topology = builder.build();

        System.out.println(topology.describe());

        final KafkaStreams streams = new KafkaStreams(topology, props);

        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            streams.close();
            latch.countDown();
        }, "streams-shutdown-hook"));

        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }

    /*
     * Sem esse método ele não consegue resolver as chaves dos tópicos
     */
    static <T> Serde<T> getPrimitiveAvroSerde(final Properties envProps, boolean isKey) {
        final KafkaAvroDeserializer deserializer = new KafkaAvroDeserializer();
        final KafkaAvroSerializer serializer = new KafkaAvroSerializer();
        final Map<String, String> config = new HashMap<>();
        config.put(SCHEMA_REGISTRY_URL_CONFIG, envProps.getProperty("schema.registry.url"));
        deserializer.configure(config, isKey);
        serializer.configure(config, isKey);
        return (Serde<T>) Serdes.serdeFrom(serializer, deserializer);
    }

    static <T extends SpecificRecord> SpecificAvroSerde<T> getSpecificAvroSerde(final Properties envProps) {
        final SpecificAvroSerde<T> specificAvroSerde = new SpecificAvroSerde<>();

        final HashMap<String, String> serdeConfig = new HashMap<>();
        serdeConfig.put(SCHEMA_REGISTRY_URL_CONFIG, envProps.getProperty("schema.registry.url"));

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