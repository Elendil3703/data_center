# Flink-Kafka-MySQL配置教程
配置步骤：https://blog.csdn.net/qq_30503389/article/details/130972489?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-12-130972489-blog-125446082.235^v43^pc_blog_bottom_relevance_base5&spm=1001.2101.3001.4242.7&utm_relevant_index=15
https://zhuanlan.zhihu.com/p/98669227

## 1. 配置数据库
```sql
create database mydb;
create table user(
    id bigint primary key auto_increment,
    name varchar(255)
);
INSERT INTO mydb.user (name) VALUES ('小明');
INSERT INTO mydb.user (name) VALUES ('小红');

```
开启数据库binlog日志

## 2. 下载kafka
下载kafka后解压，进入其目录
启动zookeeper服务
```shell
bin/zookeeper-server-start.sh config/zookeeper.properties
```
启动kafka服务
```shell
bin/kafka-server-start.sh config/server.properties
```
## 3. 运行消费者脚本
```shell
python3 comsumer.py
```
## 4. 运行生产者脚本
运行CustomSink.java
## 5. 效果
可以在消费者脚本中看到数据库从建库以来的所有变动