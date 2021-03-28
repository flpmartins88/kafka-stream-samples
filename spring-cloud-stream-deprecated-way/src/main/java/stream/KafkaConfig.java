package stream;

import java.util.HashMap;
import java.util.Map;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.flpmartins88.streams.events.CustomerEvent;
import io.flpmartins88.streams.events.OrderCompletedEvent;
import io.flpmartins88.streams.events.OrderEvent;
import io.flpmartins88.streams.events.PaymentEvent;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.messaging.handler.annotation.SendTo;

@EnableKafka
@Configuration
@EnableBinding(KafkaConfig.KafkaStreamBinder.class)
public class KafkaConfig {

//    @Bean
//    public Function<KTable<String, PaymentEvent>,
//            Function<KTable<String, OrderEvent>,
//            Function<KTable<String, CustomerEvent>,
//            KStream<String, OrderCompletedEvent>>>> enrichOrder() {
//        return payment -> (
//            order -> (
//                customer -> (
//                    payment.join(order, p -> p.getSource().getId(), new OrderPaymentJoiner())
//                            .join(customer, o -> o.getCustomer().getId(), new OrderCustomerJoiner())
//                            .toStream()
//                )
//            )
//        );
//    }

    @Bean
    public Serde<SpecificRecord> avroInSerde(){
        final SpecificAvroSerde<SpecificRecord> avroInSerde = new SpecificAvroSerde<>();
        Map<String, Object> serdeProperties = new HashMap<>();
        return avroInSerde;
    }

//    @Bean
//    <T extends SpecificRecord> SpecificAvroSerde<T> serde() {
//        SpecificAvroSerde<T> serde = new SpecificAvroSerde<>();
//        var props = new HashMap<String, String>();
//        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
//        serde.configure(props, false);
//        return serde;
//    }
//
//
//
//    @Bean
//    <T> Serde<T> getPrimitiveAvroSerde() {
//        final KafkaAvroDeserializer deserializer = new KafkaAvroDeserializer();
//        final KafkaAvroSerializer serializer = new KafkaAvroSerializer();
//        final Map<String, String> config = new HashMap<>();
//        config.put(SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
//        deserializer.configure(config, true);
//        serializer.configure(config, true);
//        return (Serde<T>) Serdes.serdeFrom(serializer, deserializer);
//    }

    @StreamListener
    @SendTo("order_completed")
    public KStream<String, OrderCompletedEvent> process(
            @Input("payment") KTable<String, PaymentEvent> paymentTable,
            @Input("order") KTable<String, OrderEvent> orderTable,
            @Input("customer") KTable<String, CustomerEvent> customerTable
    ) {
        return paymentTable.join(orderTable, p -> p.getSource().getId(), new OrderPaymentJoiner())
                .join(customerTable, o -> o.getCustomer().getId(), new OrderCustomerJoiner())
                .toStream();
    }

    interface KafkaStreamBinder {
        @Input("payment")
        KTable<String, PaymentEvent> paymentTable();

        @Input("order")
        KTable<String, OrderEvent> orderTable();

        @Input("customer")
        KTable<String, CustomerEvent> customerTable();

        @Output("order_completed")
        KStream<String, OrderCompletedEvent> orderCompletedStream();
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