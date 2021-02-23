package com.example.myproject.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: yp
 * @time: 2021/2/2017:13
 * @description: kafka 配置
 */

@Configuration
@EnableKafka
@ComponentScan
public class KafkaConfig {

    @Value("${kafka.bootstrap.servers:localhost}")
    private String bootstrapServers;
    @Value("${kafka.producer.batch.size:16384}")
    private int batchSize;
    @Value("${kafka.producer.linger.ms:100}")
    private int lingerMs;
    @Value("${kafka.producer.max.request.size:20480}")
    private int maxRequestSize;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> props = new HashMap<>(8);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize); // 到达16bk就发
        props.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs); // 时间到达100ms就发
        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, maxRequestSize); // 每次请求最大20kb
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory(), true);
    }

    @Bean
    public ConsumerFactory<String, StringDeserializer> consumerFactory() {
        Map<String, Object> props = new HashMap<>(8);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);

    }

    @Bean
    public KafkaListenerContainerFactory kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, StringDeserializer> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConcurrency(3);
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }
//
//    @Component
//    static class Listener {
//
//        @KafkaListener(id="client_one",topics = "test")
//        public void receive(String message) {
//            System.out.println("收到的消息为:" + message);
//        }
//        @KafkaListener(id="client_two",topics = "test1")
//        public void receive(Integer message) {
//            System.out.println("收到的的Integer消息为:" + message);
//        }
//
//    }
}
