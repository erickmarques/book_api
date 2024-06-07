# Book API

Book API é uma API simples de cadastro de livros que integra com a Google Book API. Ela foi desenvolvida utilizando Java 17 e as seguintes tecnologias: Spring Boot 3.3.0, Spring Data, Spring Cloud 2023.0.2, Spring Security, Spring Cloud OpenFeign, Eureka Client, Swagger, banco de dados H2, Flyway e Gradle.

## Índice

- [Descrição](#descrição)
- [Instalação](#instalação)
- [Uso](#uso)
- [Arquitetura](#arquitetura)

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

## Uso

### Endpoints Disponíveis

- `POST /login`: Gera um token JWT após autenticação bem-sucedida.
- `POST /api/books`: Cria um novo livro.
- `PUT /api/books/{id}`: Atualiza um livro.
- `DELETE /api/books/{id}`: Deleta um livro.
- `GET /api/books`: Lista todos os livros.
- `GET /api/books/{id}`: Obtém detalhes de um livro específico (ao pesquisar um livro pelo ID, o contador é incrementado).
- `GET /api/books/google-books`: Pesquisa na Google Book API.

### Exemplos de Uso

#### Autenticação e Geração de Token

```sh
curl -X POST http://localhost:8080/login -H "Content-Type: application/json" -d '{"username": "user", "password": "password"}'
 ```

 ```sh
 curl -X POST http://localhost:8080/api/books -H "Authorization: Bearer <seu_token_jwt>" -H "Content-Type: application/json" -d '{"title": "Spring in Action", "author": "Craig Walls"}'
  ```

## Arquitetura

- **Book API**: Microserviço para gerenciar operações de cadastro de livros e integração com a Google Book API.
- **Eureka Server**: Serve como servidor de descoberta de serviços.
- **Gateway**: Utilizado para rotear requisições para os microserviços apropriados.

Fique à vontade para contribuir com este projeto.



