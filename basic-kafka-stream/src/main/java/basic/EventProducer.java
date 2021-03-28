package basic;

import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import io.flpmartins88.streams.events.CustomerEvent;
import io.flpmartins88.streams.events.OrderEvent;
import io.flpmartins88.streams.events.OrderItemEvent;
import io.flpmartins88.streams.events.PaymentEvent;
import io.flpmartins88.streams.events.PaymentSource;
import io.flpmartins88.streams.events.PaymentStatus;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class EventProducer {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        IntFunction<OrderItemEvent> itemCreatorFunction = i -> OrderItemEvent.newBuilder()
                .setName(String.format("Item %03d", i))
                .setQuantity(new Random().nextInt(10 - 1) + 1)
                .setUnitAmount(new Random().nextInt(10000 - 100) + 100)
                .build();

        var items = IntStream.range(1, 5)
                .mapToObj(itemCreatorFunction)
                .collect(Collectors.toList());

        var customer = CustomerEvent.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setName("Customer sem nome")
                .build();

        var order = OrderEvent.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setCustomer(customer.getId())
                .setItems(items)
                .build();

        var source = PaymentSource.newBuilder()
                .setId(order.getId())
                .setName("order")
                .build();

        var charge = PaymentEvent.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setResult(PaymentStatus.PAID)
                .setAmount(100)
                .setSource(source)
                .build();

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");

        new KafkaProducer<String, OrderEvent>(props).send(
                new ProducerRecord<>("order", order.getId(), order),
                (metadata, exception) -> System.out.println(metadata)
        ).get();

        new KafkaProducer<String, CustomerEvent>(props).send(
                new ProducerRecord<>("customer", customer.getId(), customer),
                (metadata, exception) -> System.out.println(metadata)
        ).get();

        new KafkaProducer<String, PaymentEvent>(props).send(
                new ProducerRecord<>("payment", charge.getId(), charge),
                (metadata, exception) -> System.out.println(metadata)
        ).get();

    }

}
