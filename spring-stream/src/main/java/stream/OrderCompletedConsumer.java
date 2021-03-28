package stream;

import org.apache.avro.specific.SpecificRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCompletedConsumer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @KafkaListener(topics = "order_completed")
    public void consume(SpecificRecord record) {
        log.info("Consuming event: " + record.toString());
    }

}
