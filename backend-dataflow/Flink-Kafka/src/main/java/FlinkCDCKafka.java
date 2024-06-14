import org.example.KafkaSink;
import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class FlinkCDCKafka {
    public static void main(String[] args) throws Exception {
        // 设置 JVM 参数
        String jvmArgs = System.getProperty("jdk.module.open.java.base");
        if (jvmArgs == null || !jvmArgs.contains("ALL-UNNAMED")) {
            System.setProperty("jdk.module.open.java.base", "ALL-UNNAMED");
        }

        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("../../config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load configuration file", e);
        }

        // 提取所有表的前缀
        Set<String> tablePrefixes = properties.stringPropertyNames().stream()
                .map(name -> name.split("\\.")[0])
                .collect(Collectors.toSet());

        // 配置Flink流环境
        Configuration configuration = new Configuration();
        configuration.setInteger(RestOptions.PORT, 8081); // 设置Flink Rest端口
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(configuration);

        // 启用检查点
        env.enableCheckpointing(5000);

        for (String prefix : tablePrefixes) {
            String hostname = properties.getProperty(prefix + ".hostname");
            int port = Integer.parseInt(properties.getProperty(prefix + ".port"));
            String database = properties.getProperty(prefix + ".database");
            String table = properties.getProperty(prefix + ".table");
            String username = properties.getProperty(prefix + ".username");
            String password = properties.getProperty(prefix + ".password");

            // 创建 MySQL CDC Source
            MySqlSource<String> source = MySqlSource.<String>builder()
                    .hostname(hostname)
                    .port(port)
                    .databaseList(database)
                    .tableList(table) // 设置要监控的表
                    .username(username)
                    .password(password)
                    .deserializer(new JsonDebeziumDeserializationSchema())
                    .includeSchemaChanges(true)
                    .build();

            // 从MySQL Source读取数据
            DataStream<String> stream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "MySQL Source");

            // 添加 Kafka Sink
            String topic = "";
            String[] values = table.split("\\.");
            topic = values[0] + "_" + values[1];
            stream.addSink(new KafkaSink(topic));
        }

        env.execute("Flink CDC with Kafka Sink for Multiple MySQL Tables");
    }
}
