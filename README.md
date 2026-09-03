# Atlas Travel Manager

API REST e interface web Angular para organizar viagens de forma simples, com autenticação JWT e dados isolados por usuário.

## Destaques

- Java 17 + Spring Boot 3 + Spring Data JPA
- Registro/login com JWT e senhas protegidas por BCrypt
- CRUD de viagens com DTOs, Bean Validation e respostas HTTP consistentes
- PostgreSQL reproduzível via Docker Compose (H2 é usado apenas nos testes)
- Paginação `page`/`size` (tamanho máximo 50)
- Frontend responsivo servido pelo próprio Spring Boot
- Dockerfile multi-stage e Docker Compose
- Angular standalone com Reactive Forms, service HTTP e estados de loading/erro/vazio

## Executar localmente

Pré-requisito: Java 17, Maven 3.9+, Node 22+ e Docker (para PostgreSQL).

```bash
docker compose up --build
```

Abra http://localhost:8080. A API está disponível em `/api`, e a documentação Swagger em http://localhost:8080/swagger-ui.html.
Em produção, defina `POSTGRES_*` e um `JWT_SECRET` forte no ambiente antes de iniciar o Compose.

Para desenvolver o frontend separadamente, em outro terminal:

```bash
cd frontend
npm.cmd install
npm.cmd start
```

Abra http://localhost:4200. O proxy encaminha `/api` para o backend em `localhost:8080`.

Para executar o backend sem Docker, configure `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`,
`SPRING_DATASOURCE_PASSWORD` e `JWT_SECRET`, então execute:

```bash
mvn spring-boot:run
```

## Autenticação e contrato rápido

Crie uma conta e use o token retornado nas rotas de viagens:

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"voce@example.com","password":"senha-segura"}'

curl "http://localhost:8080/api/travels?page=0&size=20" \
  -H "Authorization: Bearer SEU_TOKEN"
```

| Método | Rota | Descrição |
|---|---|---|
| POST | `/api/auth/register` | Registra usuário e retorna JWT |
| POST | `/api/auth/login` | Autentica usuário e retorna JWT |
| GET | `/api/travels?page=0&size=20` | Lista somente suas viagens (máximo 50 por página) |
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

`Angular -> JWT REST Controller -> Service -> Repository -> PostgreSQL`, com `TravelRequest`/`TravelResponse` isolando o contrato HTTP da entidade JPA. O frontend mantém o token, envia o header Bearer e exibe páginas de viagens.

## Integração contínua

O workflow `.github/workflows/ci.yml` executa testes Maven, build Angular e build da imagem Docker em pushes e pull requests.
