## Project Infrastructure
This project will be my playground. I chose the e-commerce site as the domain. The technologies and approaches I will use are as follows.

```
Architecture     : Hexagonal (Ports & Adapters) - Microservice
Programming lang.: Java 11
Framework        : Spring boot 2.5.1 - Spring Cloud 2020.0.3 
Databases        : Cassandra, Mongo, Neo4j, Elasticsearch
Monitoring       : Zipkin, Kibana, Grafana
Build Management : Maven, Docker
Communication    : Rest, Kafka(event), gRPC
```

In general, I will try most technologies in the project. For example, some microservices will communicate with rest and some with grpc.<p>
**Release time** : Unknown

## Maven Structure
Our Maven structure will be as follows

+ base (base pom)
    + order-service (base pom)
        + order-service-domain
        + order-service-client
        + order-service-infra
          + order-service-client
          + order-service-domain
          + other microservices clients
        
    + payment-service (base pom)
        + same as order ...
    + ...


By adding another layer (client's) to the hexagonal architecture, we will control the models to be sent between micro services.

**Domain** : We will only have domain structure,  <span style="color: red">it is not dependent on framework,infra or client</span> <p>
**infra**:  Depending on framework <p>
**Client**: Same as domain, we just manage dto's ( Data Transfer Object ) <p>

## Database per service

```
recommendation-s.-> Neo4j
order-service    -> MongoDB
payment-service  -> MongoDB
shipping-service -> MongoDB
user-service     -> CassandraDB
product-service  -> CassandraDB
search-service   -> Elasticsearch
```