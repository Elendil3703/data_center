@echo off
java --add-opens java.base/java.lang=ALL-UNNAMED -jar target/data_query-0.0.1-SNAPSHOT.jar --spring.config.location=target/classes/application.properties
pause
