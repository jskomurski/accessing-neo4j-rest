# Neo4j REST Example 

Example application using Spring Boot starter with Neo4j via Bolt driver.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

- Neo4j Server
- Java
  - Java 8 used for compile of application
  - Java 11 used for Neo4j 4.0+

### Neo4j setup

Start Neo$j

```
$ neo4j start
```

#### Permissions to Access Neo4j
Neo4j Community Edition requires credentials to access it. You can configure the credentials by setting properties in `src/main/resources/application.properties` , as follows:

```
spring.data.neo4j.username=neo4j
spring.data.neo4j.password=secret
```

This includes the default username (neo4j) and the newly set password (secret) that you set earlier.

Best practice is not store credentials in source repository:
[source](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-external-config)

Credentials can be configured at runtime like the following:

```
SPRING_APPLICATION_JSON='{"spring.data.neo4j":{"username":"neo4j","password":"secret"}}'
```

## Installing

If you use Maven, you can run the application by using 

```
./mvnw spring-boot:run
```

Alternatively, you can build the JAR file with the supplied `mvnw` file and then run the JAR file, as follows:

```
./mvnw clean package

java -jar target/gs-accessing-neo4j-data-rest-0.1.0.jar
```

## Server deployment

Follow steps to create startup script for systemd service [link](https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html#deployment-systemd-service)

Create `accessing-neo4j-rest.service`:

```
[Unit]
Description=accessing-neo4j-rest
After=syslog.target

[Service]
User=ec2-user
ExecStart=/usr/lib/jvm/java-1.8.0/jre/bin/java -jar /var/app/accessing-neo4j-rest.jar
SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
```

Start service:

```
sudo systemctl start accessing-neo4j-rest.service
```

## Usage

Use __GET__ `/employees` endpoint to return all employee nodes back

```shell
$ curl localhost:8080/employees
```
```json
{
  "_embedded" : {
    "employees" : [ ]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/employees{?page,size,sort}",
      "templated" : true
    },
    "profile" : {
      "href" : "http://localhost:8080/profile/employees"
    },
    "search" : {
      "href" : "http://localhost:8080/employees/search"
    }
  },
  "page" : {
    "size" : 20,
    "totalElements" : 0,
    "totalPages" : 0,
    "number" : 0
  }
```

Use __POST__ `/employees` with the following data to create a new employee:

```json
{"employeeId" : "id", "employeeName" : "name"}'
```

Example to create new employee John Smith with employee id of 1:

```shell
$ curl -X POST -H "Content-Type:application/json" -d '{"employeeId" : "1", "employeeName" : "John Smith"}' http://localhost:8080/employees
```
```json
{
  "employeeName" : "John Smith",
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/employees/1"
    },
    "employee" : {
      "href" : "http://localhost:8080/employees/1"
    }
  }
```

