package com.example.myproject;

import com.example.myproject.kafka.consumer.KafkaConsumer;
import com.example.myproject.kafka.produce.KafkaProduce;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@SpringBootTest
class MyprojectApplicationTests {

    @Autowired
    private KafkaProduce kafkaProduce;

    @Autowired
    private KafkaConsumer kafkaConsumer;

    @Test
    void sendMessageSync() throws InterruptedException, ExecutionException, TimeoutException {
        String tip = "test";
        kafkaProduce.sendMessageSync(tip, "123");
    }
}
