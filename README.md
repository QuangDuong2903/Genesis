![UIT](https://img.shields.io/badge/from-UIT%20VNUHCM-blue?style=for-the-badge&link=https%3A%2F%2Fwww.uit.edu.vn%2F)

# Genesis

### Contributors

- Dinh Quang Duong - 21520751@gm.uit.edu.vn - [Github](https://github.com/doublek2712)
- Ngo Quang Khai - 21520953@gm.uit.edu.vn - [Github](https://github.com/QuangDuong2903)

### Supervisors

- Nguyen Cong Hoan - hoannc@uit.edu.vn

### Description

Genesis is a sample microservice codebase built for our Project 1 at UIT.
This project includes most common patterns in MSA, especially:
- Saga
- CQRS (query service)

![Alt Text](https://drive.google.com/uc?id=1uSIkfQbSvfEQ2MeJtS9HoQGm6AkZPNwu)

We implement a basic saga flow _Create order_ for demo.

![Alt Text](https://drive.google.com/uc?id=15c6B5LH4wzZF2SdH3fToB5qL6Zp6YVAM)

Create order operation can be represented by statemachine diagram so we use
[Spring Statemachine](https://spring.io/projects/spring-statemachine) as saga orchestration 

![Alt Text](https://drive.google.com/uc?id=1cBQ3Y5fn5Gc1K_3I5MmmOFL3OKjLrCkM)

### Technologies and Framework
- Java 17
- Spring Boot 3: Authorization Server (OAuth 2), Statemachine ...
- Spring Cloud Gateway, Open Feign, Stream ...
- Elastic stack: Elasticsearch, Logstash, Kibana, Filebeat
- Hashicorp stack: Vault, Consul
- Grafana stack: Prometheus, Grafana
- Zipkin
- Redis

### How to use:

Create network
```commandline
docker network create -d bridge genesis-network
```
Run infra
```commandline
docker compose -f docker-compose.infra.yml up -d
```
Enable consul acl
```commandline
docker exec -it genesis-infra-consul consul acl bootstrap
```

Remember the SecretID printed on the command line. It is your consul token.

```commandline
docker exec -it genesis-infra-consul sh
```
```commandline
export CONSUL_HTTP_TOKEN={YOUR_CONSUL_TOKEN}
```

Access the Vault dashboard at `localhost:8200`.
For the first time, Vault required you provide the number of Key shares and Key threshold.
For simplicity, just enter 1 and click **Initialize**.
Vault will provide you 2 keys. The **Initial root token** is the key for authentication when you login to vault dashboard and 
when the service tries to fetch secret from it. The second key is for unsealing vault.

Access the vault dashboard by the key has been generated, choose **Secrets Engines**
then click **Enable new engine**. Choose **KV**, set path is **genesis** and click **Enable Engine**.
Now you have to create secret for each service, click **Create secret**. For example with auth-service, enter _auth-service/dev_
for field **Path for this secret**. You can enable the Json above and paste this as **Secret data**
```json
{
  "CONSUL_HOST": "genesis-infra-consul",
  "CONSUL_PORT": "8500",
  "CONSUL_TOKEN": "200274eb-bdf5-3324-d065-28ecd61ff8be",
  "DB_HOST": "52.79.219.185",
  "DB_PASSWORD": "123456",
  "DB_PORT": "5432",
  "DB_USERNAME": "quangduong",
  "RABBITMQ_HOST": "52.79.219.185",
  "RABBITMQ_PASSWORD": "123456",
  "RABBITMQ_PORT": "5672",
  "RABBITMQ_USERNAME": "quangduong",
  "ZIPKIN_HOST": "52.79.219.185",
  "ZIPKIN_PORT": "9411"
}
```
> **_NOTE:_** You have to change the value depend on your system.

Identify all env variable in _application.yml_ file and do the same with other services.
