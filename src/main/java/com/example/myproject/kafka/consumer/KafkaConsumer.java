package com.example.myproject.kafka.consumer;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.common.serialization.StringDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: yp
 * @time: 2021/2/23 10:31
 * @description:
 */
@Slf4j
@Component
public class KafkaConsumer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /*
     * kafka 批量处理
     */
    public List<KafkaMessage<?>> messagesBatch(List<ConsumerRecord<String, String>> list){
        List<KafkaMessage<?>> messages = new ArrayList<>();
        Set<String> dataSet = new HashSet<>();
        for (ConsumerRecord<String, String> record : list) {
            String value = record.value();
            try {
                if (dataSet.contains(value)) {
                    continue;
                }
                dataSet.add(value);
                KafkaMessage<?> message = JSON.toJavaObject(JSON.parseObject(value), KafkaMessage.class);
                if (message == null) {
                    continue;
                }
                messages.add(message);
            } catch (Exception e) {
                logger.error("此条数据处理异常：{}",value);
                e.printStackTrace();
            }
        }
        return messages;
    }
    /*
     * kafka 单个处理
     */
    public String messageSingle(ConsumerRecord<String, StringDeserializer> data){
        String msg = JSON.toJSONString(data.value());
        return msg;
    }

    @KafkaListener(topics = "test", groupId = "datacenter-collect", containerFactory="kafkaListenerContainerFactory")
    public String getTestMag(ConsumerRecord<String, StringDeserializer> data){
        String message= this.messageSingle(data);
        logger.info("消息 = {}",JSON.toJSONString(message));
        return message;
    }
}
