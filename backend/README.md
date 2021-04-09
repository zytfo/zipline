# Zipline backend
### Up docker compose with Postgres 
```
docker compose up -d
```
Probably you have to create a database 'db' inside the docker container, to do it simply run:
```
docker exec -it <container> psql -U postgres -c "CREATE DATABASE db;"
```

Postgres runs on ```localhost:5432```, user: ```postgres```, password: ```admin!```. You may also create the database via accessing it.

### Build
```
mvn clean install -DskipTests -Dflyway.configFiles=flyway.properties
```
### Run
```
nohup java -jar target/zipline-0.1.0-SNAPSHOT.jar &
```
To kill all java processes run ```killall java``` or find pid with ```ps -fC java``` and kill it. 

### Flyway migrations. By default, it runs during the build phase, but you can use it anytime.
```
mvn clean flyway:migrate
```

### Or you can simply do:
```
chmod +x build.sh
./build.sh
```
