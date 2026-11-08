# Simulação de Sistema Bancário

API para simular saque/depósito de um usuário.

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
---

## Arquitetura e Design de Software

* **Arquitetura Hexagonal** 
* **Microsserviços**
* **Domain-Driven Design (DDD)** 

---

## Modelo de Dados e Persistência

* **MongoDB:** banco de dados NoSQL orientado a documentos (coleção `usuario_bank`, IDs gerados pelo Mongo como `ObjectId`).
* **Conexão:** configurada pela variável de ambiente `MONGODB_URI` (padrão: `mongodb://localhost:27017/bankapi`).

---

## Logging

* **Mecanismo:** SLF4J com Logback (`log.info`/`log.warn`/`log.error`).

---

## Princípios SOLID & Design Patterns

### SOLID
* **S (Single Responsibility):** Classes com responsabilidade única 
* **O (Open/Closed):** O ecossistema está aberto para extensão e fechada para modificação.
* **L (Liskov Substitution):** Classes filhas se comportam como a classe pai.
* **I (Interface Segregation):** Interfaces de domínio enxutas.
* **D (Dependency Inversion):** interface que serve ao seu modelo de domínio (Spring Data MongoDB).

### Design Patterns
* **Singleton:** Escopo padrão dos Beans gerenciados pelo Spring Framework (Services, Repositories).

---

## Como Executar

### Pré-requisitos
* Git
* Docker e Docker Compose instalados
* Uma instância MongoDB acessível (ex.: `docker run -d -p 27017:27017 mongo:7`), apontada por `MONGODB_URI`

### Passo a Passo
1. Clone o repositório:
   ```
   git clone <url-do-repositorio>
2. Navegue até a raiz do projeto:
    ```
   cd <nome-do-projeto>
3. Renomei o arquivo
    ```
    ".env.example" para ".env"
4. Rode no console:
    ```
   docker-compose up -d --build

## Links
* O projeto irá rodar em: http://localhost:8080
* Documentação Swagger: http://localhost:8080/swagger-ui/index.html

## Listar usuários
* **URL:** `/api/v1/usuarios`
* **Método:** `GET`

#### Exemplo de Resposta (`200 OK`):

```json
[
  {
    "id": "6650f1c2a1b2c3d4e5f60718",
    "nome": "Nome",
    "cpf": "339.948.940-42",
    "saldo": 1000.00
  }
]
```
## Melhorias

* **Cache Redis**: Nos usuários para reduzir latência em leitura pesada.
## Segurança da API

Todos os endpoints da API exigem o cabeçalho `Authorization: Bearer <token>`. Configure `API_TOKEN` no ambiente antes de iniciar a aplicação. O serviço não inicia se o token não estiver configurado.

Exemplo no PowerShell:
```powershell
$env:API_TOKEN = "troque-por-um-token-seguro"
$env:MONGODB_URI = "mongodb://localhost:27017/bankapi"
./mvnw spring-boot:run
```

Exemplo de chamada:
```bash
curl -H "Authorization: Bearer troque-por-um-token-seguro" http://localhost:8080/api/v1/usuarios
```

Sem token válido, a API retorna `401 Unauthorized`. O Swagger fica público para uso local.
