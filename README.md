# Mortgage Service
## Prerequisites
- Java 21
- Maven 3.x.x
- Docker (optional, for building docker image)

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
### Actuator
http://localhost:8080/actuator

### Swagger UI
http://localhost:8080/swagger-ui/index.html

### Api Spec
json: http://localhost:8080/v3/api-docs

yaml: http://localhost:8080/v3/api-docs.yaml

## Load external data
create a yaml file like [this](mortgage-repository/mortgage-repository-in-memory/src/main/resources/application-data.yaml) file and set spring boot additional location property like this: `SPRING_CONFIG_ADDITIONAL_LOCATION: PATH_TO_YAML_FILE`
