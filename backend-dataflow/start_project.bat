@echo off

REM Docker 部署
docker-compose -p sse_demo up --build -d

REM 切换到脚本目录并启动Java项目
cd Flink-Kafka
start cmd /k "start_java.bat"
echo Java project have been started.
cd ../

REM 启动Python项目
cd consumers
start cmd /k "start_python.bat"
echo Python project have been started.
cd ../

echo Java and Python projects have been started.
pause
