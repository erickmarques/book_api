# Book API

Book API é uma API simples de cadastro de livros que integra com a Google Book API. Ela foi desenvolvida utilizando Java 17 e as seguintes tecnologias: Spring Boot 3.3.0, Spring Data, Spring Cloud 2023.0.2, Spring Security, Spring Cloud OpenFeign, Eureka Client, Swagger, banco de dados H2, Flyway e Gradle.

## Índice

- [Descrição](#descrição)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Uso](#uso)
- [Arquitetura](#arquitetura)
- [Contribuição](#contribuição)
- [Licença](#licença)
- [Autores e Agradecimentos](#autores-e-agradecimentos)

## Descrição

O projeto Book API permite o cadastro, atualização, remoção e consulta de livros, bem como a pesquisa de livros na Google Book API. Ele utiliza JWT para autenticação e está registrado no Book Eureka Server para descoberta de serviços.

## Instalação

### Pré-requisitos

- [Java 17+](https://adoptopenjdk.net/)
- [Gradle 7.5+](https://gradle.org/)
- [Docker](https://www.docker.com/) (opcional para rodar o Eureka Server)

### Passos para Instalação

1. Clone o repositório:
    ```sh
    git clone https://github.com/seu-usuario/book-api.git
    cd book-api
    ```

2. Configure o Eureka Server:
    - Certifique-se de que o Eureka Server está em execução e configurado corretamente.

3. Configure o banco de dados H2:
    - A aplicação já vem configurada para utilizar o banco de dados H2 em memória. As migrações são gerenciadas pelo Flyway.

4. Compile e rode a aplicação:
    ```sh
    ./gradlew bootRun
    ```

## Configuração

### Variáveis de Ambiente

- `EUREKA_SERVER_URL`: URL do servidor Eureka (default: `http://localhost:8761/eureka`)
- `JWT_SECRET`: Segredo utilizado para assinar o token JWT

### Arquivo de Configuração

Certifique-se de que os arquivos `application.properties` ou `application.yml` estejam configurados corretamente. Exemplo de `application.properties`:

```properties
spring.application.name=book-api
server.port=8080

# Eureka
eureka.client.service-url.defaultZone=http://localhost:8761/eureka

# Segurança
spring.security.oauth2.resourceserver.jwt.issuer-uri=http://localhost:8081/realms/mscourserealm
jwt.secret=my-secret-key

# H2 Database
spring.datasource.url=jdbc:h2:mem:bookdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Flyway
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
