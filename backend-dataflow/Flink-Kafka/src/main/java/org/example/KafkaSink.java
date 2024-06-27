package org.example;

import com.ververica.cdc.connectors.shaded.com.fasterxml.jackson.databind.JsonNode;
import com.ververica.cdc.connectors.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import com.ververica.cdc.connectors.shaded.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaSink extends RichSinkFunction<String> {
    public transient Producer<String, String> producer;
    public final String topic;


    public KafkaSink(String topic) {
        this.topic = topic;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092"); // Kafka服务器地址
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
    }

    @Override
    public void invoke(String value, Context context) throws Exception {
        // 解析原始JSON数据，假设value是从某处获取到的JSON字符串
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(value);

        // 构建新的JSON对象
        ObjectNode newJsonNode = objectMapper.createObjectNode();

        // 判断操作类型
        if(rootNode.get("after").isEmpty())
        {
            // 解析before字段的值作为一个新的JSON对象
            JsonNode beforeNode = objectMapper.readTree(rootNode.get("before").toString());
            System.out.println(beforeNode.toString());
            // 将before字段下的所有字段加入到这个新的节点中
            newJsonNode.setAll((ObjectNode) beforeNode);
            newJsonNode.put("label", true);
        }
        else
        {
            // 解析after字段的值作为一个新的JSON对象
            JsonNode afterNode = objectMapper.readTree(rootNode.get("after").toString());

            // 将after字段下的所有字段加入到这个新的节点中
            newJsonNode.setAll((ObjectNode) afterNode);
            newJsonNode.put("label", false);
        }

        // 将新的JSON对象转换成字符串，然后发送到Kafka
        String jsonToSend = objectMapper.writeValueAsString(newJsonNode);

        System.out.println("Sending to Kafka topic: " + topic + " " +  jsonToSend);

        producer.send(new ProducerRecord<>(topic, jsonToSend), (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
            } else {
                System.out.println("Message sent to Kafka topic " + topic + ": " + metadata.topic() + "-" + metadata.partition() + "@" + metadata.offset());
            }
        });
    }

    @Override
    public void close() throws Exception {
        producer.close();
        super.close();
    }
}
