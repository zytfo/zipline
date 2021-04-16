#!/bin/sh

git pull

killall java

mvn clean install -DskipTests -Dflyway.configFiles=flyway.properties

nohup java -Dspring.profiles.active=server -Xmx2048m -jar target/zipline-0.1.0-SNAPSHOT.jar &
