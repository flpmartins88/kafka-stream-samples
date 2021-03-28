Projeto de estudo do KafkaStream Standalone (No futuro terá a versão com Spring e/ou Spring Cloud Stream)

Baseado em: https://kafka-tutorials.confluent.io/foreign-key-joins/kstreams.html


Para executar precisa subir os containers do docker definidos no docker-compose.yml.

Cada uma das três classes tem uma função:
- OrderCompletedConsumer: Para ver o resultado do stream
- OrderProducer: Grava a combinação dos dados nos tópicos
- EventStream: Emite um novo evento com base no que foi lido dos outros 3 tópicos