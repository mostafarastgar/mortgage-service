# Mortgage Service
## Build
### build locally
```shell
mvn clean install
```
### build with docker image locally
```shell
mvn clean install -Pmortgage-in-memory,docker-build -Ddocker.jib.goal=dockerBuild
```
### build with docker image
```shell
mvn clean install -Pmortgage-in-memory,docker-build
```
## Start docker
```shell
docker run --rm -p 8080:8080 mortgage-service:in-memory-0.0.1-SNAPSHOT
```
## View
http://localhost:8080/swagger-ui/index.html
