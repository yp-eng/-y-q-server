package com.example.myproject.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author: yp
 * @time: 2021/2/2310:07
 * @description:
 */
@Slf4j
@Component
public class KafkaConsumerListener {
//    @KafkaListener(topics = {"test"}, containerFactory="kafkaListenerContainerFactory")
//    public String kafkaListener(String message){
//        System.out.println("收到的消息为:" + message);
//        return message;
//    }
}
