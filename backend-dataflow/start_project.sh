#!/bin/bash

# docker 部署
docker-compose -p sse_demo up --build -d

# 项目路径
JAVA_PROJECT_PATH="./Flink-Kafka/target/Flink-Kafka-1.0-SNAPSHOT-shaded.jar"
PYTHON_PROJECT_PATH="./consumers/main.py"

# 启动Java项目
start_java_project() {
    java --add-opens java.base/java.lang=ALL-UNNAMED -jar $JAVA_PROJECT_PATH &
    echo "Java project have been started."
}

# 启动Python项目
start_python_project() {
    python $PYTHON_PROJECT_PATH &
    echo "Java project have been started."
}

# 启动Java和Python项目
start_java_project
start_python_project

# 等待所有后台进程完成
wait

echo "Java and Python projects have been started."
