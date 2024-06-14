import requests
import json
from kafka import KafkaConsumer
from elasticsearch import Elasticsearch

# Kafka 配置
KAFKA_TOPIC = 'db_1151.user_info'
KAFKA_GROUP_ID = 'group2'
KAFKA_SERVERS = ['localhost:9092']

# 初始化 Elasticsearch 客户端
es = Elasticsearch(
    ['http://127.0.0.1:9200'],
    headers={"Content-Type": "application/json"}
)

# 需要的索引名称
index_name = "user_info_es"

# 初始化 Kafka 消费者
consumer = KafkaConsumer(
    KAFKA_TOPIC,
    group_id=KAFKA_GROUP_ID,
    bootstrap_servers=KAFKA_SERVERS,
    auto_offset_reset='earliest'
)

def send_data_to_es(data, delete=False):
    data_load = json.loads(data)
    print(data_load)
    if delete:
        es.delete(index=index_name, id=data_load['UserID'])
    else:
        es.index(index=index_name, id=data_load['UserID'], body=data)

def consume_single_message():
    for msg in consumer:
        message = msg.value.decode('utf-8')
        data = json.loads(message)
        if 'label' in data and data['label']:
            send_data_to_es(message, delete=True)
        else:
            send_data_to_es(message, delete=False)

consume_single_message()