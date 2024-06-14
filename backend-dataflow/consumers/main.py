import threading
import json
from kafka import KafkaConsumer
from elasticsearch import Elasticsearch
import configparser

# 读取配置文件
config = configparser.ConfigParser()
config.read('config.properties')

# 初始化 Elasticsearch 客户端
es = Elasticsearch(
    [config['elasticsearch']['url']],
    headers={"Content-Type": "application/json"}
)

# 获取所有 Kafka 主题配置
kafka_topics = [key for key in config['kafka'] if key.startswith('topic') and key.endswith('.name')]
print(kafka_topics)

# 初始化 Kafka 消费者和其对应id
consumers = {}
ids = {}
for topic in kafka_topics:
    topic_name = config['kafka'][topic]
    group_id_key = topic.replace('.name', '.group_id')
    servers_key = topic.replace('.name', '.servers')
    id_key = topic.replace('.name', '.id')

    group_id = config['kafka'][group_id_key]
    servers = config['kafka'][servers_key].split(',')

    print(config['kafka'][id_key])

    consumer = KafkaConsumer(
        topic_name,
        group_id=group_id,
        bootstrap_servers=servers,
        auto_offset_reset='earliest'
    )
    consumers[topic_name] = consumer

    ids[topic_name] = config['kafka'][id_key]


def send_data_to_es(data, index_name, id_name, delete=False):
    data_load = json.loads(data)
    print(data_load)
    if delete:
        es.delete(index=index_name, id=id_name)
    else:
        es.index(index=index_name, id=id_name, body=data)


# def consume_messages():
#     for topic_name, consumer in consumers.items():
#         id_name = ids[topic_name]
#         for msg in consumer:
#             message = msg.value.decode('utf-8')
#             data = json.loads(message)
#             if 'label' in data and data['label']:
#                 send_data_to_es(message, index_name=topic_name, id_name=id_name, delete=True)
#             else:
#                 send_data_to_es(message, index_name=topic_name, id_name=id_name, delete=False)

def consume_topic_messages(topic_name, consumer, id_name):
    for msg in consumer:
        message = msg.value.decode('utf-8')
        data = json.loads(message)
        if 'label' in data and data['label']:
            send_data_to_es(message, index_name=topic_name, id_name=data[id_name], delete=True)
        else:
            send_data_to_es(message, index_name=topic_name, id_name=data[id_name], delete=False)

def consume_messages():
    threads = []
    for topic_name, consumer in consumers.items():
        id_name = ids[topic_name]
        thread = threading.Thread(target=consume_topic_messages, args=(topic_name, consumer, id_name))
        threads.append(thread)
        thread.start()

    # 等待所有线程完成
    for thread in threads:
        thread.join()



consume_messages()
