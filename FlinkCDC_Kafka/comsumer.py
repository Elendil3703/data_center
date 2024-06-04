#!/usr/bin/env python3
from kafka import KafkaConsumer

# 初始化 Kafka 消费者
consumer = KafkaConsumer(
	'test',
	group_id='group2',
	bootstrap_servers=['localhost:9092'],
	auto_offset_reset='earliest'
)

# 消费消息
for msg in consumer:
	print(f"Received message: {msg.value.decode('utf-8')}")
	
	