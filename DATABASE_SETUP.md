# 🗄️ Configuração do Banco de Dados - Junara

## PostgreSQL Setup

### 1️⃣ Instalar PostgreSQL
- Download: https://www.postgresql.org/download/
- Versão recomendada: 15+

### 2️⃣ Criar Bancos de Dados

Conecte ao PostgreSQL e execute:

```sql
-- Banco de Desenvolvimento
CREATE DATABASE junara_dev;

-- Banco de Produção
CREATE DATABASE junara;
```

### 3️⃣ Atualizar Credenciais

Edite os arquivos de configuração com sua senha PostgreSQL:

**`src/main/resources/application-dev.properties`**
```properties
spring.datasource.password=SUA_SENHA_AQUI
```

**`src/main/resources/application-prod.properties`**
```properties
spring.datasource.password=SUA_SENHA_AQUI
```

---

## 🚀 Executar Aplicação

### Desenvolvimento (padrão)
```bash
mvn spring-boot:run
```
- Banco: `junara_dev`
- Console: http://localhost:8080
- DDL: `update` (cria/altera tabelas automaticamente)

### Produção
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
```
- Banco: `junara`
- DDL: `validate` (apenas valida esquema)

---

## 📊 Verificar Banco via psql

```bash
psql -U postgres -d junara_dev
```

---

## ⚙️ Dependências Adicionadas
- `org.postgresql:postgresql` - Driver PostgreSQL
- `org.springframework.boot:spring-boot-starter-data-jpa` - JPA/Hibernate
- `org.projectlombok:lombok` - Reduzir boilerplate

---

## 🔍 Logs e Debug

**Dev**: SQL queries são impressas (log level DEBUG para `com.junaradelivery`)
**Prod**: Apenas warnings e errors (log level WARN)

---

## ✅ Verificação

```bash
# Compilar
mvn clean compile

# Testes
mvn test

# Build final
mvn clean package
```

Dúvidas? Verifique as configurações em:
- `src/main/resources/application.properties`
- `src/main/resources/application-dev.properties`
- `src/main/resources/application-prod.properties`
