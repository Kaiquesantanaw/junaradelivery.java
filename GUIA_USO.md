# 📖 GUIA DE USO - JUNARA DELIVERY

## 🎯 Objetivo
Este guia mostra como usar o sistema Junara no dia-a-dia para gerenciar seu delivery.

---

## 📍 FASE 1: PREPARAÇÃO INICIAL

### Passo 1: Iniciar o Sistema
```bash
cd junara
mvn spring-boot:run
```

**Aguarde até ver:**
```
Tomcat started on port(s): 8081
```

### Passo 2: Verificar se está funcionando
Abra no navegador: `http://localhost:8081`

Você deve ver a página inicial com o logo **👨‍🍳 Junara**

---

## 🛍️ FASE 2: CADASTRAR SEUS PRODUTOS

### Onde: `http://localhost:8081/admin`

### Passo 1: Ir para aba "📦 Produtos"
1. Acesse o painel admin
2. Clique na aba **📦 Produtos** (segunda aba)

### Passo 2: Adicionar Primeiro Produto
Preencha o formulário:

```
Nome: Pizza Mozzarela
Preço (R$): 45.90
Descrição: Deliciosa pizza com mozzarela derretida e orégano
URL da Imagem: https://via.placeholder.com/400x400?text=Pizza
```

Clique: **Salvar Produto**

### Passo 3: Adicionar Mais Produtos
Repita para cada item do seu cardápio:

**Exemplo 1:**
```
Nome: Pastel de Frango
Preço: 8.50
Descrição: Pastel crocante recheado com frango desfiado
URL: https://via.placeholder.com/400x400?text=Pastel
```

**Exemplo 2:**
```
Nome: Refrigerante 2L
Preço: 12.00
Descrição: Refrigerante gelado
URL: https://via.placeholder.com/400x400?text=Refri
```

**Exemplo 3:**
```
Nome: Sorvete
Preço: 15.00
Descrição: Sorvete artesanal sabor chocolate
URL: https://via.placeholder.com/400x400?text=Sorvete
```

### ✅ Status
Você deve ver seus 3+ produtos listados com foto!

---

## 👥 FASE 3: CADASTRO DE CLIENTES (AUTOMÁTICO)

**Boas notícias:** Os clientes se registram sozinhos quando fazem pedido!

Mas você pode gerenciá-los na aba **👥 Clientes** do admin:
- Ver todos os clientes
- Editar dados
- Deletar (se necessário)

---

## 📦 FASE 4: RECEBER E GERENCIAR PEDIDOS

### Passo 1: Compartilhe o Link do Shop
Envie para seus clientes:
```
http://localhost:8081/shop
```

(Se estiver em servidor público, use seu domínio)

### Passo 2: Cliente Faz Pedido
1. Cliente acessa o shop
2. Vê foto de cada produto
3. Clica em quantidade e "Adicionar"
4. Preenche dados (nome, email, telefone, endereço)
5. Clica "Confirmar Pedido"

### Passo 3: Você Recebe o Pedido
1. Acesse: `http://localhost:8081/admin`
2. Aba **🛍️ Pedidos**
3. Você vê:
   - Nome do cliente
   - Telefone
   - Endereço
   - Produtos pedidos
   - Valor total
   - Status: **PENDENTE** (vermelho)

### Passo 4: Confirmar Pedido
1. Clique em "PENDENTE" (ou botão de status)
2. Mude para **CONFIRMADO**
3. Cliente sabe que você aceitou

Fluxo dos status:
```
PENDENTE (vermelho)
    ↓
CONFIRMADO (amarelo) - Você aceitou!
    ↓
ENVIADO (azul) - Saiu para entrega
    ↓
ENTREGUE (verde) - Pronto!
```

### Passo 5: Entregar e Finalizar
Quando entregar:
1. Mude status para **ENTREGUE**
2. Pedido fica marcado como concluído ✅

---

## 📊 PHASE 5: ACOMPANHAR RESULTADOS

### Dashboard (primeira aba "📊")
Você vê:
- **Total de Clientes**: Quantos clientes tem
- **Total de Produtos**: Quantos itens do cardápio
- **Pedidos**: Quantos pedidos foram feitos
- **Valor Total**: Quanto vendeu

---

## ⚙️ DICAS PRÁTICAS

### 💡 Dica 1: Fotos dos Produtos
Use URLs grátis:
- **Placeholder**: `https://via.placeholder.com/400x400?text=SEU_TEXTO`
- **Unsplash**: Procure imagem lá e copie URL

### 💡 Dica 2: Atualizar Preço
Se precisar mudar preço de um produto:
1. Clique no botão **Editar** do produto
2. Altere o preço
3. Salve

### 💡 Dica 3: Remover Produto
Se parar de oferecer:
1. Clique **Deletar** na linha do produto
2. Confirme

### 💡 Dica 4: Verificar Cliente
Antes de entregar:
1. Copie o telefone do cliente
2. Mande mensagem confirmando endereço
3. Atualize Whatsapp se necessário (edite cliente)

### 💡 Dica 5: Horário de Funcionamento
Sugestão: Desativar /shop fora do horário
(Será adicionado em breve)

---

## 📱 FLUXO COMPLETO DO CLIENTE

```
1. Cliente recebe link: http://localhost:8081/shop

2. Acessa no navegador
   
3. Vê cardápio com fotos

4. Seleciona produtos:
   - Pizza x2
   - Pastel x1
   - Refrigerante x1

5. Carrinho mostra:
   - Subtotal: R$ 102.40
   - Taxa: R$ 5.00
   - Total: R$ 107.40

6. Preenche endereço e dados

7. Clica "Confirmar Pedido - R$ 107.40"

8. Modal de sucesso: ✅ Pedido #123 confirmado!

9. Você recebe notificação no /admin

10. Você vê pedido pendente

11. Você confirma (PENDENTE → CONFIRMADO)

12. Você envia (CONFIRMADO → ENVIADO)

13. Você entrega (ENVIADO → ENTREGUE)
```

---

## 🆘 PROBLEMAS COMUNS

### Problema 1: Porto 8081 em uso
**Solução:**
```bash
# Windows
netstat -ano | findstr :8081
taskkill /PID <número> /F

# Linux
lsof -i :8081
kill -9 <PID>
```

### Problema 2: Erro ao adicionar produto
**Solução:**
- Verifique se preço está em formato número (45.90)
- Nome não pode estar vazio
- Imagem pode deixar em branco (vai usar emoji)

### Problema 3: Pedido não aparece
**Solução:**
- Atualizar a página (F5)
- Verificar se formulário foi preenchido completo

### Problema 4: Cliente não vê produtos
**Solução:**
- Verifique se produtos foram criados no admin
- Atualizar página do shop (F5)

---

## 🎨 CUSTOMIZAR APARÊNCIA

Você pode editar cores/logo em:
```
src/main/resources/templates/shop.html
src/main/resources/templates/index.html
```

Procure por:
- `#FF9800` - Cor laranja (mude se quiser)
- `👨‍🍳` - Logo (mude emoji se quiser)
- `Junara` - Nome do sistema

---

## 📝 ROTINA DIÁRIA

### Manhã
- [ ] Ligar o sistema: `mvn spring-boot:run`
- [ ] Verificar se todos produtos estão no cardápio
- [ ] Atualizar preços se necessário

### Durante o dia
- [ ] Verificar `/admin` → **Pedidos**
- [ ] Confirmar pedidos novos
- [ ] Marcar como "ENVIADO" ao sair para entrega
- [ ] Marcar como "ENTREGUE" ao chegar no cliente

### Noite
- [ ] Ver relatório de vendas no dashboard
- [ ] Desligar o sistema (CTRL+C)

---

## 🚀 PRÓXIMOS PASSOS

1. **Autenticação**: Adicionar senha para o admin
2. **WhatsApp**: Integração para avisar cliente
3. **Relatórios**: Gerar PDF com vendas do mês
4. **Notificações**: Som/push quando pedido chega
5. **Deploy**: Colocar em servidor público

---

## 💬 DÚVIDAS?

Releia o README.md ou consulte DATABASE_SETUP.md

**Bom trabalho com seu delivery! 🍕🚚**
