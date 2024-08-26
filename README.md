# Projeto Teste Técnico Simples Dental

A API desenvolvida é uma proposta para teste de conhecimentos para desenvolvimento back-end.

# 1 - Descrição do Projeto

O projeto representa uma pequena aplicação de controle para profissionais e seus contatos. Desenvolvido utilizando a linguagem Java, arquitetura MVC e testes unitários para os Use Cases.


# 2 - Tecnologias

- [Java 17](https://docs.oracle.com/en/java/javase/17/)
- [SpringBoot 3.3.3](https://docs.spring.io/spring-boot/api/java/index.html)
- [Postgresql](https://www.postgresql.org/about/news/postgresql-13-released-2077/)
- [Flyway](https://documentation.red-gate.com/flyway)
- [Swagger 3](https://swagger.io/docs/)
- [Junit 5](https://junit.org/junit5/docs/current/user-guide/)
- [h2](https://h2database.com/html/quickstart.html)
- [Jacoco](https://www.jacoco.org/jacoco/trunk/doc/)

# 3 - Arquitetura da API

A arquitetura escolhida foi a MVC, conforme solicitado na documentação do teste. Para promover o desacoplamento, foram implementadas algumas práticas adicionais, como o uso de interfaces que isolam o framework das interfaces de regras de negócio (service) e repositórios (repository).

Além disso, foi adotada a estrutura "Command" para a comunicação entre os endpoints (Controllers) e as regras de negócio (Services). Para garantir que os serviços não tenham acesso direto às entidades da API, foi criada uma camada de regras específica para o repositório, enquanto a persistência e as operações no banco de dados são gerenciadas por uma interface separada, acoplada ao framework (RepositoryJPA).

# 4 - Funcionalidades

## Profissionais

| Método | Descrição |
|---|---|
| `GET` | Listar e filtrar os profissionais |
| `GET/{id}` | Consultar um profissional por id |
| `POST` | Criação de um novo profissional |
| `PUT/{id}` | Editar as informações de um profissional |
| `DELETE/{id}` |  Inativar um profissional |

## Contatos

| Método | Descrição |
|---|---|
| `GET` | Listar e filtrar os contatos |
| `GET/{id}` | Consultar um contato por id |
| `POST` | Criação de um novo contato |
| `PUT/{id}` | Editar as informações de um contato |
| `DELETE/{id}` |  Remover um contato |

# 5 - Javadocs

Acesse a [javadocs do projeto.](https://roblopes7.github.io/simples-dental-teste/docs/)

# 6 - Pré-requisitos

Para usar localmente é necessário o Java 17 e banco de dados Postgres.

Para o build usar o comando:

   ```bash
   mvn clean install
  ```

  Variáveis de ambiente para execução

  - DB_HOST: Ip do local do banco / Padrão(localhost);
  - DB_PORT: Porta do banco / Padrão(5432);
  - DATABASE: Nome da base de dados / Padrão (simples_dental)
  - DB_USERNAME: Usuario do banco de dados / Padrão(postgres)
  - DB_PASSWORD: Senha do banco de dados / Padrão (root)
  - APP_PORT= Porta da API / Padrão(8080)


# 7 - Docker

Para subir a API pelo docker, defina as váriaveis de ambiente e use o comando:

   ```bash
   docker-compose up
  ```
# 8 - Swagger

Para acessar os endpoints disponíveis, consulte o swagger: http://localhost:8080/swagger-ui/index.html#/ caso esteja usando outra porta ou ip alterar.

# 9 - DockerHub

   ```bash
roblopes7/simples-dental-teste:1.0
  ```

# 10 - Sugestões de melhorias futuras

Adicionar testes unitários a camada Repository e testes de integração.
