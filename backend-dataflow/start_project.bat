@echo off

REM Docker 部署
docker-compose -p sse_demo up --build -d

REM 启动Java项目
start cmd /c "java --add-opens java.base/java.lang=ALL-UNNAMED -jar target/Flink-Kafka-1.0-SNAPSHOT-shaded.jar"
echo Java project have been started.

REM 启动Python项目
start cmd /c "python .\consumers\main.py"
echo Python project have been started.

echo Java and Python projects have been started.
pause
