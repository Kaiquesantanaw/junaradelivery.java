# 👨‍🍳 JUNARA - Sistema de Delivery

> **Sistema completo para gerenciar pedidos, produtos e clientes de um delivery**

## 🚀 Início Rápido

### 1️⃣ Pré-requisitos
- Java 21+
- Maven 3.8+
- (Opcional) PostgreSQL 15+ para produção

### 2️⃣ Executar Localmente
```bash
cd junara
mvn spring-boot:run
```

### 3️⃣ Acessar o Sistema
- 🛍️ **Loja** (Clientes fazem pedidos): `http://localhost:8081/shop`
- 👨‍💼 **Admin** (Você gerencia tudo): `http://localhost:8081/admin`
- 📊 **API**: `http://localhost:8081/api/v1/`

---

## 📋 Funcionalidades Principais

### 🛒 Para Seus Clientes (Página `/shop`)
- ✅ Visualizar cardápio com foto dos produtos
- ✅ Adicionar produtos ao carrinho
- ✅ Inserir dados de entrega
- ✅ Fazer pedido online
- ✅ Receber confirmação do pedido

### 👨‍💼 Para Você (Painel `/admin`)

#### **📦 Pedidos**
- Visualizar todos os pedidos em tempo real
- Ver detalhes: cliente, telefone, endereço, produtos
- Atualizar status: PENDENTE → CONFIRMADO → ENVIADO → ENTREGUE
- Deletar pedidos incorretos

#### **👥 Clientes**
- Listar todos os clientes cadastrados
- Ver endereço, email, telefone
- Editar dados do cliente
- Deletar clientes

#### **🍕 Produtos**
- Adicionar novos produtos com:
  - Nome
  - Descrição
  - Preço
  - **Foto** (URL da imagem)
- Editar produto
- Deletar produto

#### **📊 Dashboard**
- Total de clientes
- Total de produtos
- Total de pedidos
- Valor total de vendas

---

## 🗂️ Estrutura do Projeto

```
junara/
├── src/main/java/com/junaradelivery/junara/
│   ├── controller/          # Endpoints da API
│   ├── service/             # Lógica de negócio
│   ├── repository/          # Acesso ao banco
│   ├── entity/              # Modelos (Cliente, Produto, Pedido)
│   ├── exception/           # Tratamento de erros
│   ├── dto/                 # DTOs para requisições
│   └── config/              # Configurações (CORS)
│
├── src/main/resources/
│   ├── templates/           # HTML do shop e admin
│   │   ├── index.html      # Painel administrativo
│   │   └── shop.html       # Loja pública
│   └── application-h2.properties  # Config desenvolvimento
│
├── pom.xml                  # Dependências Maven
├── DATABASE_SETUP.md        # Setup do banco PostgreSQL
└── README.md               # Este arquivo
```

---

## 🛠️ Como Usar

### **1. Adicionar Produtos**
1. Acesse: `http://localhost:8081/admin`
2. Aba **📦 Produtos**
3. Preencha:
   - Nome (ex: "Pizza Mozzarela")
   - Preço (ex: 45.90)
   - Descrição (ex: "Pizza com queijo derretido")
   - URL da Imagem (ex: `https://via.placeholder.com/400x400?text=Pizza`)
4. Clique **Salvar Produto**

### **2. Receber Pedidos**
1. Clientes acessam: `http://localhost:8081/shop`
2. Selecionam produtos, adicionam ao carrinho
3. Preenchem dados de entrega
4. Clicam "Confirmar Pedido"

### **3. Gerenciar Pedidos**
1. Acesse: `http://localhost:8081/admin`
2. Aba **🛍️ Pedidos**
3. Veja todos os pedidos com status **PENDENTE**
4. Clique em "Atualizar Status" e mude para:
   - ✅ **CONFIRMADO** - Pedido aceito
   - 📦 **ENVIADO** - Em entrega
   - 🚚 **ENTREGUE** - Finalizado

---

## 📊 Exemplo de Fluxo Completo

```
1. Você adiciona produtos no /admin (Pizza, Pastel, Refrigerante)
   ↓
2. Cliente acessa /shop
   ↓
3. Cliente seleciona: 1x Pizza + 2x Pastel
   ↓
4. Preenche: Nome, Email, Telefone, Endereço
   ↓
5. Clica "Confirmar Pedido"
   ↓
6. Pedido aparece em /admin (Status: PENDENTE)
   ↓
7. Você confirma (Status: CONFIRMADO)
   ↓
8. Você marca como enviado (Status: ENVIADO)
   ↓
9. Você marca como entregue (Status: ENTREGUE)
   ↓
10. Pedido finalizado! ✅
```

---

## 🔗 API REST

Todos os endpoints em `/api/v1/`:

### **Clientes**
```
GET    /clientes              # Listar todos
POST   /clientes              # Criar
PUT    /clientes/{id}         # Atualizar
DELETE /clientes/{id}         # Deletar
```

### **Produtos**
```
GET    /produtos              # Listar todos
POST   /produtos              # Criar
PUT    /produtos/{id}         # Atualizar
DELETE /produtos/{id}         # Deletar
```

### **Pedidos**
```
GET    /pedidos                # Listar todos
POST   /pedidos/criar          # Criar (usado pelo shop)
PUT    /pedidos/{id}           # Atualizar status
DELETE /pedidos/{id}           # Deletar
```

---

## 📸 Fotos de Produtos

Você pode usar URLs de:
- ✅ **Unsplash**: `https://images.unsplash.com/...`
- ✅ **Placeholder**: `https://via.placeholder.com/400x400?text=Pizza`
- ✅ **Seu servidor**: `https://seu-site.com/fotos/pizza.jpg`

---

## 🗄️ Banco de Dados

### **Desenvolvimento** (Padrão)
```
Usar: H2 In-Memory
URL: jdbc:h2:mem:testdb
Sem setup necessário!
```

### **Produção**
Para usar PostgreSQL:

1. Instale PostgreSQL 15+
2. Crie banco:
```sql
CREATE DATABASE junara;
```

3. Edite: `src/main/resources/application-prod.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nnnnn
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

4. Execute com profile prod:
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
```

---

## 🚀 Deploy

### **GitHub Pages (Cardápio público)**
- O workflow `.github/workflows/deploy-pages.yml` publica automaticamente o `shop.html` no GitHub Pages ao fazer push na `main`.
- URL final: `https://<seu-usuario>.github.io/junaradelivery.java/`
- A página publicada usa por padrão a API em:
  `https://junaradeliveryjava-production.up.railway.app`
- Para trocar a API sem editar código, adicione `?api=<url-backend>` na URL da página.

> Exemplo:
> `https://<seu-usuario>.github.io/junaradelivery.java/?api=https://seu-backend.com`

#### Backend (CORS) para GitHub Pages
No backend em produção, configure `CORS_ALLOWED_ORIGINS` com os domínios permitidos (separados por vírgula), por exemplo:

```env
CORS_ALLOWED_ORIGINS=https://junaradeliveryjava-production.up.railway.app,https://*.github.io
```

### **Criar executável JAR**
```bash
mvn clean package -DskipTests
java -jar target/junara-0.0.1-SNAPSHOT.jar
```

### **Docker** (em progresso)
```bash
# Será adicionado em breve
```

---

## 🐛 Troubleshooting

### **Porta 8081 já está em uso**
```bash
# Windows
netstat -ano | findstr :8081
taskkill /PID <ID> /F

# Linux/Mac
lsof -i :8081
kill -9 <PID>
```

### **Erro de compilação**
```bash
mvn clean compile
```

### **Necessário limpar build**
```bash
mvn clean install
```

---

## 📞 Próximas Melhorias

- [ ] Autenticação JWT para o admin
- [ ] Relatórios de vendas
- [ ] Integração com WhatsApp/SMS
- [ ] Sistema de pagamento
- [ ] Histórico de pedidos
- [ ] Notificações em tempo real
- [ ] App mobile

---

## 🏗️ Stack Tecnológico

| Tecnologia | Versão | Uso |
|-----------|--------|-----|
| Java | 21 | Backend |
| Spring Boot | 4.1.0-M1 | Framework |
| JPA/Hibernate | Latest | ORM |
| H2 | Latest | DB Dev |
| PostgreSQL | 15+ | DB Prod |
| Thymeleaf | Latest | Templates |
| JavaScript | ES6+ | Frontend |

---

## 📝 Licença

Closed Source - Propriedade de Junara Delivery

---

## 👨‍💻 Desenvolvido por

Junara Delivery

---

## 💬 Suporte

Para dúvidas ou problemas:
1. Verifique este README
2. Consulte GUIA_USO.md
3. Revise a estrutura em DATABASE_SETUP.md

---

**Sucesso com seu delivery! 🚀🍕**
