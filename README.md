# BaB Search Engine

Motor de busca desenvolvido pela BaB Consultoria.

Ambiente:
- Hardware: 7,5GB de RAM, 30GB de HD, 2 vCPU Intel Xeon E5-2670 v2, Amazon EC2 m3.large
- SO: Ubuntu 14.04, AMI Bitnami Wildfly 10.x
- Banco de Dados: Elasticsearch 5.2.2 (5 shards e 1 réplica)
- Servidor de Aplicação: Wildfly 10
- Streaming: Apache Kafka (Amazon Kinesis)
- Indexação e pagerank: Apache Spark 2.1.0
- Front-end: AngularJS, HTML5 e CSS3
- Endereço da API: http://50.19.226.130/bab-search-engine
- Endereço da página: http://50.19.226.130/bab
- Swagger: http://50.19.226.130/bab-search-engine/api/swagger.yaml