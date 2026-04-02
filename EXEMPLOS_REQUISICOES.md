# 📋 Exemplos de Test Requests - Junara API

## Base URL
```
http://localhost:8080/api/v1
```

---

## 👥 CLIENTES

### 1. Criar Cliente
```bash
POST /clientes
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao.silva@example.com",
  "telefone": "11987654321"
}
```

**Resposta 201:**
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao.silva@example.com",
  "telefone": "11987654321"
}
```

---

### 2. Listar Todos os Clientes
```bash
GET /clientes
```

**Resposta 200:**
```json
[
  {
    "id": 1,
    "nome": "João Silva",
    "email": "joao.silva@example.com",
    "telefone": "11987654321"
  },
  {
    "id": 2,
    "nome": "Maria Santos",
    "email": "maria@example.com",
    "telefone": "11998765432"
  }
]
```

---

### 3. Buscar Cliente por ID
```bash
GET /clientes/1
```

**Resposta 200:**
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao.silva@example.com",
  "telefone": "11987654321"
}
```

**Resposta 404 (não encontrado):**
```json
{
  "timestamp": "2026-03-19T21:42:22",
  "status": 404,
  "error": "Recurso não encontrado",
  "message": "Cliente não encontrado com ID: 999",
  "path": "/api/v1/clientes/999"
}
```

---

### 4. Atualizar Cliente
```bash
PUT /clientes/1
Content-Type: application/json

{
  "nome": "João Silva Oliveira",
  "email": "joao.novo@example.com"
}
```

**Resposta 200:**
```json
{
  "id": 1,
  "nome": "João Silva Oliveira",
  "email": "joao.novo@example.com",
  "telefone": "11987654321"
}
```

---

### 5. Deletar Cliente
```bash
DELETE /clientes/1
```

**Resposta 204:** (sem corpo)

---

---

## 🛍️ PRODUTOS

### 1. Criar Produto
```bash
POST /produtos
Content-Type: application/json

{
  "nome": "Hambúrguer Especial",
  "descricao": "Hambúrguer preparado com carne premium e molho especial",
  "preco": 29.90
}
```

**Resposta 201:**
```json
{
  "id": 1,
  "nome": "Hambúrguer Especial",
  "descricao": "Hambúrguer preparado com carne premium e molho especial",
  "preco": 29.90
}
```

---

### 2. Listar Todos os Produtos
```bash
GET /produtos
```

**Resposta 200:**
```json
[
  {
    "id": 1,
    "nome": "Hambúrguer Especial",
    "descricao": "Hambúrguer preparado com carne premium e molho especial",
    "preco": 29.90
  },
  {
    "id": 2,
    "nome": "Pizza Margherita",
    "descricao": "Pizza com mozzarela e tomate fresco",
    "preco": 35.00
  }
]
```

---

### 3. Buscar Produto por ID
```bash
GET /produtos/1
```

**Resposta 200:**
```json
{
  "id": 1,
  "nome": "Hambúrguer Especial",
  "descricao": "Hambúrguer preparado com carne premium e molho especial",
  "preco": 29.90
}
```

---

### 4. Atualizar Produto
```bash
PUT /produtos/1
Content-Type: application/json

{
  "nome": "Hambúrguer Premium",
  "preco": 32.90
}
```

**Resposta 200:**
```json
{
  "id": 1,
  "nome": "Hambúrguer Premium",
  "descricao": "Hambúrguer preparado com carne premium e molho especial",
  "preco": 32.90
}
```

---

### 5. Deletar Produto
```bash
DELETE /produtos/1
```

**Resposta 204:** (sem corpo)

---

## 📦 PEDIDOS

### 1. Criar Pedido
```bash
POST /pedidos
Content-Type: application/json

{
  "cliente": {
    "id": 1
  },
  "produtos": [
    {
      "id": 1
    },
    {
      "id": 2
    }
  ],
  "valorTotal": 64.90,
  "status": "PENDENTE"
}
```

**Resposta 201:**
```json
{
  "id": 1,
  "cliente": {
    "id": 1,
    "nome": "João Silva",
    "email": "joao.silva@example.com",
    "telefone": "11987654321"
  },
  "produtos": [
    {
      "id": 1,
      "nome": "Hambúrguer Especial",
      "descricao": "Hambúrguer preparado com carne premium e molho especial",
      "preco": 29.90
    },
    {
      "id": 2,
      "nome": "Pizza Margherita",
      "descricao": "Pizza com mozzarela e tomate fresco",
      "preco": 35.00
    }
  ],
  "valorTotal": 64.90,
  "status": "PENDENTE",
  "dataCriacao": "2026-03-19T21:42:22.123456",
  "dataAtualizacao": "2026-03-19T21:42:22.123456"
}
```

---

### 2. Listar Todos os Pedidos
```bash
GET /pedidos
```

**Resposta 200:**
```json
[
  {
    "id": 1,
    "cliente": {...},
    "produtos": [...],
    "valorTotal": 64.90,
    "status": "PENDENTE",
    "dataCriacao": "2026-03-19T21:42:22.123456",
    "dataAtualizacao": "2026-03-19T21:42:22.123456"
  }
]
```

---

### 3. Buscar Pedido por ID
```bash
GET /pedidos/1
```

**Resposta 200:**
```json
{
  "id": 1,
  "cliente": {...},
  "produtos": [...],
  "valorTotal": 64.90,
  "status": "PENDENTE",
  "dataCriacao": "2026-03-19T21:42:22.123456",
  "dataAtualizacao": "2026-03-19T21:42:22.123456"
}
```

---

### 4. Listar Pedidos de um Cliente
```bash
GET /pedidos/cliente/1
```

**Resposta 200:**
```json
[
  {
    "id": 1,
    "cliente": {...},
    "produtos": [...],
    "valorTotal": 64.90,
    "status": "PENDENTE",
    "dataCriacao": "2026-03-19T21:42:22.123456",
    "dataAtualizacao": "2026-03-19T21:42:22.123456"
  }
]
```

---

### 5. Listar Pedidos por Status
```bash
GET /pedidos/status/CONFIRMADO
```

**Resposta 200:**
```json
[
  {
    "id": 2,
    "cliente": {...},
    "produtos": [...],
    "valorTotal": 64.90,
    "status": "CONFIRMADO",
    "dataCriacao": "2026-03-19T21:42:22.123456",
    "dataAtualizacao": "2026-03-19T21:50:00.123456"
  }
]
```

**Status disponíveis:**
- `PENDENTE`
- `CONFIRMADO`
- `ENVIADO`
- `ENTREGUE`
- `CANCELADO`

---

### 6. Atualizar Pedido (Alterar Status)
```bash
PUT /pedidos/1
Content-Type: application/json

{
  "status": "CONFIRMADO"
}
```

**Resposta 200:**
```json
{
  "id": 1,
  "cliente": {...},
  "produtos": [...],
  "valorTotal": 64.90,
  "status": "CONFIRMADO",
  "dataCriacao": "2026-03-19T21:42:22.123456",
  "dataAtualizacao": "2026-03-19T21:50:00.123456"
}
```

---

### 7. Atualizar Tudo do Pedido
```bash
PUT /pedidos/1
Content-Type: application/json

{
  "cliente": {
    "id": 2
  },
  "produtos": [
    {"id": 2}
  ],
  "valorTotal": 35.00,
  "status": "ENVIADO"
}
```

**Resposta 200:**
```json
{
  "id": 1,
  "cliente": {...},
  "produtos": [...],
  "valorTotal": 35.00,
  "status": "ENVIADO",
  "dataCriacao": "2026-03-19T21:42:22.123456",
  "dataAtualizacao": "2026-03-19T22:00:00.123456"
}
```

---

### 8. Deletar Pedido
```bash
DELETE /pedidos/1
```

**Resposta 204:** (sem corpo)

---

## ❌ Respostas de Erro

### Erro 400 - Argumento Inválido
```json
{
  "timestamp": "2026-03-19T21:42:22",
  "status": 400,
  "error": "Argumento inválido",
  "message": "Preço deve ser maior que zero",
  "path": "/api/v1/produtos"
}
```

### Erro 404 - Recurso Não Encontrado
```json
{
  "timestamp": "2026-03-19T21:42:22",
  "status": 404,
  "error": "Recurso não encontrado",
  "message": "Pedido não encontrado com ID: 999",
  "path": "/api/v1/pedidos/999"
}
```

### Erro 500 - Erro Interno
```json
{
  "timestamp": "2026-03-19T21:42:22",
  "status": 500,
  "error": "Erro interno do servidor",
  "message": "Unexpected error occurred",
  "path": "/api/v1/clientes"
}
```

---

## 🧪 Teste com cURL (Windows)

### Criar Cliente
```cmd
curl -X POST http://localhost:8080/api/v1/clientes ^
  -H "Content-Type: application/json" ^
  -d "{\"nome\":\"João\",\"email\":\"joao@example.com\",\"telefone\":\"119999999\"}"
```

### Listar Clientes
```cmd
curl http://localhost:8080/api/v1/clientes
```

### Criar Produto
```cmd
curl -X POST http://localhost:8080/api/v1/produtos ^
  -H "Content-Type: application/json" ^
  -d "{\"nome\":\"Hambúrguer\",\"descricao\":\"Delicioso\",\"preco\":25.50}"
```

### Criar Pedido
```cmd
curl -X POST http://localhost:8080/api/v1/pedidos ^
  -H "Content-Type: application/json" ^
  -d "{\"cliente\":{\"id\":1},\"produtos\":[{\"id\":1}],\"valorTotal\":25.50}"
```

---

## 🧪 Teste com PowerShell

```powershell
# Criar Cliente
$body = @{
    nome = "João Silva"
    email = "joao@example.com"
    telefone = "11987654321"
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/api/v1/clientes" `
    -Method POST `
    -ContentType "application/json" `
    -Body $body
```

---

Divirta-se testando! 🎉
