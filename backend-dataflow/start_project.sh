#!/bin/bash

# docker 部署
docker-compose -p sse_demo up --build -d

# 启动Java项目
start_java_project() {
    cd Flink-Kafka
    ./start_java.sh &
    echo "Java project have been started."
    cd ../
}

# 启动Python项目
start_python_project() {
    cd consumers
    ./start_python.sh &
    echo "Java project have been started."
    cd ../
}

# 启动Java和Python项目
start_java_project
start_python_project

# 等待所有后台进程完成
wait

echo "Java and Python projects have been started."
