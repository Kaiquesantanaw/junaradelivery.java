# ✅ CHECKLIST DE SETUP - JUNARA DELIVERY

Use este checklist para configurar seu delivery corretamente.

---

## 🔧 PARTE 1: AMBIENTE (5 minutos)

- [ ] Java 21 instalado
  ```bash
  java -version
  ```
  
- [ ] Maven instalado
  ```bash
  mvn -version
  ```

- [ ] Projeto clonado/baixado
  ```bash
  git clone https://github.com/kaiquesantanaw/junara.git
  cd junara
  ```

- [ ] Projeto compila
  ```bash
  mvn clean compile
  ```

---

## 🚀 PARTE 2: EXECUTAR PELA PRIMEIRA VEZ (10 minutos)

- [ ] Iniciar o servidor
  ```bash
  mvn spring-boot:run
  ```

- [ ] Aguardar mensagem:
  ```
  Tomcat started on port(s): 8081
  Built project successfully
  ```

- [ ] Acessar home: `http://localhost:8081/`
  - Esperado: Página com logo 👨‍🍳 Junara

- [ ] Acessar admin: `http://localhost:8081/admin`
  - Esperado: Painel com abas (Clientes, Produtos, Pedidos, Dashboard)

- [ ] Acessar shop: `http://localhost:8081/shop`
  - Esperado: Página pública de pedidos

---

## 🍕 PARTE 3: CADASTRAR PRODUTOS (15 minutos)

- [ ] Acessar `/admin` → Aba **"📦 Produtos"**

- [ ] Adicionar Produto 1:
  ```
  Nome: Pizza Mozzarela
  Preço: 45.90
  Descrição: Pizza com mozzarela
  URL Imagem: https://via.placeholder.com/400x400?text=Pizza
  ```
  - [ ] Clicou "Salvar Produto"?
  - [ ] Produto apareceu na lista?

- [ ] Adicionar Produto 2:
  ```
  Nome: Pastel
  Preço: 8.50
  Descrição: Pastel crocante
  URL Imagem: https://via.placeholder.com/400x400?text=Pastel
  ```
  - [ ] Produto apareceu?

- [ ] Adicionar Produto 3:
  ```
  Nome: Refrigerante 2L
  Preço: 12.00
  Descrição: Refri gelado
  URL Imagem: https://via.placeholder.com/400x400?text=Refri
  ```
  - [ ] Produto apareceu?

- [ ] Total de 3+ produtos cadastrados ✅

---

## 🛒 PARTE 4: TESTAR COMO CLIENTE (10 minutos)

- [ ] Abrir NOVA ABA do navegador (não feche o admin)

- [ ] Acessar: `http://localhost:8081/shop`
  - [ ] Vê os 3 produtos com fotos?
  - [ ] As fotos carregaram?

- [ ] Adicionar ao carrinho:
  - [ ] Clique quantidade = 2
  - [ ] Clique "Adicionar" na Pizza
  - [ ] Esperado: "Pizza Mozzarela adicionado ao carrinho"

- [ ] Verificar carrinho:
  - [ ] Vê "Pizza x2" no carrinho?
  - [ ] Valor total atualizado?
  - [ ] Taxa de R$ 5,00 adicionada?

- [ ] Fazer pedido de teste:
  ```
  Nome: João Silva
  Email: joao@test.com
  Telefone: (11) 91234-5678
  Endereço: Rua Teste 123, São Paulo
  ```
  - [ ] Clicou "Confirmar Pedido"?
  - [ ] Modal de sucesso apareceu? ✅

---

## 📦 PARTE 5: VERIFICAR PEDIDO NO ADMIN (5 minutos)

- [ ] Voltar à aba do admin (ou atualizar)

- [ ] Aba **"🛍️ Pedidos"**
  - [ ] Vê o pedido #1?
  - [ ] Status = PENDENTE (vermelho)?
  - [ ] Mostra cliente "João Silva"?
  - [ ] Mostra endereço correto?
  - [ ] Mostra pizza x2?

- [ ] Clicar em editar/status do pedido
  - [ ] Mude para CONFIRMADO
  - [ ] Status ficou amarelo?

- [ ] Mude para ENVIADO
  - [ ] Status ficou azul?

- [ ] Mude para ENTREGUE
  - [ ] Status ficou verde ✅?

---

## 📊 PARTE 6: VERIFICAR DASHBOARD (3 minutos)

- [ ] Aba **"📊 Dashboard"**
  - [ ] Clientes: 1 ✅
  - [ ] Produtos: 3 ✅
  - [ ] Pedidos: 1 ✅
  - [ ] Valor total: R$ 96,80 (2×45.90 + 5.00) ✅

---

## 👥 PARTE 7: GERENCIAR CLIENTES (5 minutos)

- [ ] Aba **"👥 Clientes"**
  - [ ] Vê "João Silva" listado?
  - [ ] Mostra email, telefone, endereço?

- [ ] Clique em EDITAR do João
  - [ ] Pode alterar dados?
  - [ ] Clique SALVAR
  - [ ] Dados atualizados? ✅

---

## 🗄️ PARTE 8: CONFIGURAR BANCO POSTGRESQL (OPCIONAL)

Apenas se quiser usar banco permanente:

- [ ] PostgreSQL 15+ instalado
  ```bash
  psql --version
  ```

- [ ] Banco criado
  ```sql
  CREATE DATABASE junara;
  ```

- [ ] Editar: `src/main/resources/application-prod.properties`
  ```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/junara
  spring.datasource.username=postgres
  spring.datasource.password=sua_senha
  spring.jpa.hibernate.ddl-auto=create-drop
  ```

- [ ] Executar com profile prod
  ```bash
  mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
  ```

- [ ] Verificar se conectou ao PostgreSQL ✅

---

## 📤 PARTE 9: ENVIAR PARA GITHUB (5 minutos)

- [ ] Git já configurado
  ```bash
  git config user.name
  git config user.email
  ```

- [ ] Fazer commit das alterações
  ```bash
  git add -A
  git commit -m "Setup inicial: produtos e testes"
  ```

- [ ] Push para GitHub
  ```bash
  git push -u origin main
  ```

- [ ] Verificar em: `https://github.com/kaiquesantanaw/junara`
  - [ ] Commit apareceu? ✅

---

## 🎉 PARTE 10: PRONTO PARA USAR!

- [ ] Sistema funcionando 100%
- [ ] Produtos cadastrados
- [ ] Pedidos funcionando
- [ ] Admin operacional
- [ ] Shop visível para clientes

---

## 📋 INSTRUÇÕES PARA USO DIÁRIO

Se tudo passou, salve isto:

### **Iniciar pela manhã**
```bash
cd junara
mvn spring-boot:run
```

### **Acessar**
- Admin: `http://localhost:8081/admin`
- Shop: `http://localhost:8081/shop` (compartilhe com clientes)

### **Gerenciar pedidos**
1. Vá para `/admin` → "🛍️ Pedidos"
2. Vê pedido novo (PENDENTE)?
3. Confirme (CONFIRMADO)
4. Marque como enviado (ENVIADO)
5. Finalize (ENTREGUE)

### **Parar à noite**
```bash
CTRL+C no terminal
```

---

## 🆘 SE ALGO DER ERRADO

**Erro 1: Porta 8081 em uso**
```bash
netstat -ano | findstr :8081
taskkill /PID <número> /F
```

**Erro 2: Compilação falha**
```bash
mvn clean compile -DskipTests
```

**Erro 3: Nada funciona**
```bash
# Começar do zero
mvn clean install
mvn spring-boot:run
```

---

## ✅ CHECKLIST FINAL

Todos os checkboxes estão marcados?

- [ ] Java 21 OK
- [ ] Maven OK
- [ ] Servidor rodando OK
- [ ] Admin funciona OK
- [ ] Shop funciona OK
- [ ] 3+ produtos cadastrados OK
- [ ] Pedido de teste criado OK
- [ ] Pedido apareceu no admin OK
- [ ] Status atualizado OK
- [ ] Dashboard mostra dados OK
- [ ] GitHub sincronizado OK

**Se SIM em todos ⬆️ = Você está pronto para receber pedidos! 🎉🍕**

---

**Sucesso! Qualquer dúvida, consulte GUIA_USO.md**
