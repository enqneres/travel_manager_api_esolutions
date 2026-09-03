# Atlas Travel Manager

API REST e interface web Angular para organizar viagens de forma simples. Este projeto evoluiu de um exercício introdutório de Spring Boot para uma aplicação demonstrável, com validação, persistência, testes e execução reproduzível.

## Destaques

- Java 17 + Spring Boot 3 + Spring Data JPA
- CRUD de viagens com DTOs, Bean Validation e respostas HTTP consistentes
- H2 para desenvolvimento local, sem dependência externa
- Frontend responsivo servido pelo próprio Spring Boot
- Dockerfile multi-stage e Docker Compose
- Angular standalone com Reactive Forms, service HTTP e estados de loading/erro/vazio

## Executar localmente

Pré-requisito: Java 17, Maven 3.9+ e Node 22+.

```bash
cd frontend
npm.cmd install
npm.cmd run build
cd ..
mvn spring-boot:run
```

Abra http://localhost:8080. A API está disponível em `/api/travels`.

Para desenvolver o frontend separadamente, em outro terminal:

```bash
cd frontend
npm.cmd install
npm.cmd start
```

Abra http://localhost:4200. O proxy encaminha `/api` para o backend em `localhost:8080`.

## Executar com Docker

```bash
docker compose up --build
```

## Contrato rápido

```bash
curl -X POST http://localhost:8080/api/travels \
  -H "Content-Type: application/json" \
  -d '{"destination":"Lisboa","country":"Portugal","startDate":"2026-10-10","endDate":"2026-10-17","status":"PLANNED","notes":"Conhecer Alfama"}'
```

| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/travels` | Lista viagens |
| GET | `/api/travels/{id}` | Consulta uma viagem |
| POST | `/api/travels` | Cria uma viagem |
| PUT | `/api/travels/{id}` | Atualiza uma viagem |
| DELETE | `/api/travels/{id}` | Remove uma viagem |

Campos obrigatórios: `destination`, `country`, `startDate`, `endDate` e `status`. A data final não pode ser anterior à inicial.

## Testes

```bash
mvn test
```

## Arquitetura

`Angular -> REST Controller -> Service -> Repository -> H2`, com `TravelRequest`/`TravelResponse` isolando o contrato HTTP da entidade JPA. O frontend usa Reactive Forms, `TravelService` e possui estados de carregamento, erro e lista vazia.

## Próximos passos

Autenticação por usuário, PostgreSQL em produção, paginação, OpenAPI/Swagger e pipeline CI são evoluções naturais para uma segunda versão.
