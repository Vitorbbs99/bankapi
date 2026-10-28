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

* **H2:**   banco de dados relacional leve e integrado que roda diretamente na memória RAM.
* **Interface Visual Local:** H2 Console.

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
* **D (Dependency Inversion):** interface que serve ao seu modelo de domínio (JPA).

### Design Patterns
* **Singleton:** Escopo padrão dos Beans gerenciados pelo Spring Framework (Services, Repositories).

---

## Como Executar

### Pré-requisitos
* Git
* Docker e Docker Compose instalados

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
    "id": 1,
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
./mvnw spring-boot:run
```

Exemplo de chamada:
```bash
curl -H "Authorization: Bearer troque-por-um-token-seguro" http://localhost:8080/api/v1/usuarios
```

Sem token válido, a API retorna `401 Unauthorized`. Swagger e console H2 ficam públicos para uso local.
