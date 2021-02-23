package com.example.myproject.kafka.consumer;

import com.alibaba.fastjson.JSON;

/**
 * 用于包装Kafka消息的实体类
 * @ate 2019年1月2日
 * @author chenyongchao
 * @param <T>
 */
public class KafkaMessage<T> {

	private int event;
	private String dataType;
	private T data;
	
	public KafkaMessage() {}
	
	public KafkaMessage(T data, int event, String dataType) {
		this.event = event;
		this.data = data;
		this.dataType = dataType;
	}
	
	public int getEvent() {
		return event;
	}

	public void setEvent(int event) {
		this.event = event;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public String toJsonString() {
		return  JSON.toJSONString(this);
	}
	
}
