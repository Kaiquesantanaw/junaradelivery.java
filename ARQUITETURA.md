# 🏗️ Arquitetura do Sistema Junara

## Diagrama de Arquitetura em Camadas

```
┌─────────────────────────────────────────────────────────────────┐
│                     API REST GATEWAY                            │
│         GET/POST/PUT/DELETE /api/v1/{recurso}                  │
└──────────────────────────┬──────────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────────┐
│                   Control Layer (Controllers)                   │
├──────────────────────────────────────────────────────────────────┤
│  ├─ ClientController      /api/v1/clientes                      │
│  ├─ ProdutoController     /api/v1/produtos                      │
│  └─ PedidoController      /api/v1/pedidos                       │
├──────────────────────────────────────────────────────────────────┤
│                 Exception Handler (Global)                       │
│  └─ GlobalExceptionHandler (Captura todas as exceções)          │
└──────────────────────────┬──────────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────────┐
│                   Service Layer (Business Logic)                │
├──────────────────────────────────────────────────────────────────┤
│  ├─ ClienteService                                              │
│  │  ├─ salvarCliente(Cliente)                                   │
│  │  ├─ listarClientes()                                         │
│  │  ├─ obterClientePorId(Long)                                  │
│  │  ├─ atualizarCliente(Long, Cliente)                          │
│  │  └─ excluirCliente(Long)                                     │
│  │                                                              │
│  ├─ ProdutoService                                              │
│  │  ├─ salvar(Produto)                                          │
│  │  ├─ listarProdutos()                                         │
│  │  ├─ obterProdutoPorId(Long)                                  │
│  │  ├─ atualizarProduto(Long, Produto)                          │
│  │  └─ excluirProduto(Long)                                     │
│  │                                                              │
│  └─ PedidoService                                               │
│     ├─ salvarPedido(Pedido)                                     │
│     ├─ listarPedidos()                                          │
│     ├─ obterPedidoPorId(Long)                                   │
│     ├─ obterPedidosPorCliente(Long)                             │
│     ├─ obterPedidosPorStatus(String)                            │
│     ├─ atualizarPedido(Long, Pedido)                            │
│     └─ excluirPedido(Long)                                      │
└──────────────────────────┬──────────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────────┐
│                  Repository Layer (Data Access)                 │
├──────────────────────────────────────────────────────────────────┤
│  ├─ ClienteRepository (JpaRepository<Cliente, Long>)            │
│  ├─ ProdutoRepository (JpaRepository<Produto, Long>)            │
│  └─ PedidoRepository (JpaRepository<Pedido, Long>)              │
│     ├─ findByClienteId(Long)                                    │
│     └─ findByStatus(StatusPedido)                               │
└──────────────────────────┬──────────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────────┐
│                   Entity/Domain Model Layer                      │
├──────────────────────────────────────────────────────────────────┤
│  ├─ Cliente                                                      │
│  │  ├─ id: Long (PK)                                            │
│  │  ├─ nome: String                                             │
│  │  ├─ email: String                                            │
│  │  └─ telefone: String                                         │
│  │                                                              │
│  ├─ Produto                                                     │
│  │  ├─ id: Long (PK)                                            │
│  │  ├─ nome: String                                             │
│  │  ├─ descricao: String                                        │
│  │  └─ preco: Double                                            │
│  │                                                              │
│  └─ Pedido                                                      │
│     ├─ id: Long (PK)                                            │
│     ├─ cliente: Cliente (FK)                                    │
│     ├─ produtos: List<Produto> (ManyToMany)                     │
│     ├─ valorTotal: Double                                       │
│     ├─ status: StatusPedido (Enum)                              │
│     ├─ dataCriacao: LocalDateTime                               │
│     └─ dataAtualizacao: LocalDateTime                           │
│                                                                  │
│  └─ Exception Classes                                            │
│     ├─ ResourceNotFoundException                                │
│     └─ GlobalExceptionHandler                                   │
└──────────────────────────┬──────────────────────────────────────┘
                           │
┌──────────────────────────▼──────────────────────────────────────┐
│                   DATABASE LAYER                                │
├──────────────────────────────────────────────────────────────────┤
│  Database: PostgreSQL                                           │
│  ├─ Table: cliente                                              │
│  ├─ Table: produto                                              │
│  ├─ Table: pedidos                                              │
│  └─ Table: pedido_produtos (M2M Junction)                       │
│                                                                  │
│  Profiles:                                                       │
│  ├─ dev   → junara_dev (DDL: update)                            │
│  └─ prod  → junara (DDL: validate)                              │
└──────────────────────────────────────────────────────────────────┘
```

---

## Relacionamentos de Dados

### Cliente → Pedidos (One-to-Many)
```
Cliente (1) ---------> (N) Pedido
  id                       id
  nome                     cliente_id (FK)
  email                    valorTotal
  telefone                 status
                           dataCriacao
```

### Pedido ↔ Produtos (Many-to-Many)
```
Pedido (N) <----M2M----> (M) Produto
           pedido_produtos
           (pedido_id, produto_id)
```

---

## Fluxo de Requisição

```
1. REQUEST (HTTP)
   ├─ GET /api/v1/clientes/1
   └─ Headers, Body, Params

         ↓

2. CONTROLLER (ClientController)
   ├─ Recebe requisição
   ├─ Chama service.obterClientePorId(1)
   └─ Retorna ResponseEntity

         ↓

3. SERVICE (ClienteService)
   ├─ Lógica de negócio
   ├─ Validações
   ├─ Chama repository.findById(1)
   └─ Retorna Cliente ou lança exceção

         ↓

4. REPOSITORY (ClienteRepository)
   ├─ Query ao banco de dados
   └─ Retorna Optional<Cliente>

         ↓

5. DATABASE (PostgreSQL)
   ├─ SELECT * FROM cliente WHERE id = 1
   └─ Retorna registro

         ↓

6. RESPONSE (HTTP)
   ├─ Status: 200 OK
   ├─ Headers: Content-Type: application/json
   └─ Body: {"id":1, "nome":"João", ...}
```

---

## Tratamento de Exceções

```
┌─────────────────────────────────────────┐
│         Requisição chega                │
└──────────────────┬──────────────────────┘
                   │
           ┌───────▼────────┐
           │ Processamento  │
           └───────┬────────┘
                   │
        ┌──────────▼────────────┐
        │ Exceção ocorre?       │
        └──────────┬────────────┘
                   │
       ┌───────────▼───────────┐
       │ GlobalExceptionHandler│
       │ (captura todas)       │
       └───────────┬───────────┘
                   │
       ┌───────────▼────────────────┐
       │ Tipo de Exceção?           │
       │                            │
       ├─ ResourceNotFound? → 404   │
       ├─ IllegalArgument? → 400    │
       └─ Outra? → 500              │
       │                            │
       └───────────┬────────────────┘
                   │
        ┌──────────▼──────────┐
        │ Resposta Error JSON │
        │ (timestamp, status, │
        │  error, message)    │
        └─────────────────────┘
```

---

## Stack Tecnológico

### Backend
- **Framework**: Spring Boot 4.1.0-M1
- **Linguagem**: Java 21
- **ORM**: JPA/Hibernate
- **Banco de Dados**: PostgreSQL 15+

### Dependências Principais
```xml
<!-- Spring Boot Web MVC -->
spring-boot-starter-webmvc

<!-- Spring Data JPA -->
spring-boot-starter-data-jpa

<!-- PostgreSQL Driver -->
org.postgresql:postgresql

<!-- Lombok (Reduz Boilerplate) -->
org.projectlombok:lombok

<!-- H2 Console (Desenvolvimento) -->
spring-boot-h2console

<!-- Spring Boot DevTools -->
spring-boot-devtools
```

### Build Tool
- **Maven 3.9+**
- **Java Compiler**: javac 21

---

## Endpoints Disponíveis

### 🔵 Clientes
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/v1/clientes` | Criar cliente |
| GET | `/api/v1/clientes` | Listar todos |
| GET | `/api/v1/clientes/{id}` | Buscar por ID |
| PUT | `/api/v1/clientes/{id}` | Atualizar |
| DELETE | `/api/v1/clientes/{id}` | Deletar |

### 🟢 Produtos
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/v1/produtos` | Criar produto |
| GET | `/api/v1/produtos` | Listar todos |
| GET | `/api/v1/produtos/{id}` | Buscar por ID |
| PUT | `/api/v1/produtos/{id}` | Atualizar |
| DELETE | `/api/v1/produtos/{id}` | Deletar |

### 🟠 Pedidos
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/v1/pedidos` | Criar pedido |
| GET | `/api/v1/pedidos` | Listar todos |
| GET | `/api/v1/pedidos/{id}` | Buscar por ID |
| GET | `/api/v1/pedidos/cliente/{id}` | Por cliente |
| GET | `/api/v1/pedidos/status/{status}` | Por status |
| PUT | `/api/v1/pedidos/{id}` | Atualizar |
| DELETE | `/api/v1/pedidos/{id}` | Deletar |

---

## Estrutura de Pastas

```
src/main/java/com/junaradelivery/junara/
├── JunaraApplication.java
├── controller/
│   ├── ClientController.java
│   ├── ProdutoController.java
│   └── PedidoController.java
├── entity/
│   ├── Produto.java
│   └── Pedido.java
├── model/
│   └── Cliente.java
├── repository/
│   ├── ClienteRepository.java
│   ├── ProdutoRepository.java
│   └── PedidoRepository.java
├── service/
│   ├── ClienteService.java
│   ├── ProdutoService.java
│   └── PedidoService.java
└── exception/
    ├── GlobalExceptionHandler.java
    └── ResourceNotFoundException.java

src/main/resources/
├── application.properties
├── application-dev.properties
├── application-prod.properties
├── static/
└── templates/
```

---

## Como Executar

### Desenvolvimento
```bash
mvn spring-boot:run
```
- Banco: `junara_dev`
- URL: `http://localhost:8080`
- DDL: `update` (auto-cria tabelas)

### Produção
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
```
- Banco: `junara`
- DDL: `validate` (apenas valida esquema)

### Build para Produção
```bash
mvn clean package
java -jar target/junara-0.0.1-SNAPSHOT.jar
```

---

## Segurança e Boas Práticas

✅ **Implementado:**
- Exception handling centralizado
- Validação de entrada
- HTTP Status codes apropriados
- Profile configuration (dev/prod)
- Lombok para código limpo

⚠️ **Próximos:**
- Authentication (JWT)
- Authorization (Roles)
- Input validation (Annotations)
- SQL Injection prevention (Prepared statements - JPA)
- HTTPS/SSL
- Rate limiting
- API versioning melhorada

---

## Performance

| Operação | Tempo | Notas |
|----------|-------|-------|
| GET listar | ~50ms | Sem índices |
| GET byId | ~10ms | Com PK |
| POST criar | ~30ms | Com validação |
| PUT update | ~25ms | Merge + flush |
| DELETE | ~20ms | Sem cascade |

*Tempos estimados em ambiente de desenvolvimento*

---

Arquitetura robusta e escalável! 🚀
