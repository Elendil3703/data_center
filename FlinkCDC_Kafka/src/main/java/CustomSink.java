import com.ververica.cdc.connectors.mysql.source.*;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class CustomSink extends RichSinkFunction<String> {
    private transient Producer<String, String> producer;

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
        producer.send(new ProducerRecord<>("test", value)); // 发送到test主题
        System.out.println("json->: " + value);
    }

    @Override
    public void close() throws Exception {
        producer.close();
        super.close();
    }


}
