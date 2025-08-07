# 📦 Módulo de Pedidos - Sistema de Vendas

Este repositório contém a implementação do **módulo de Pedidos** de um sistema de vendas. Ele expõe uma API RESTful que permite:

- 📝 **Efetuar Pedido** (`POST /pedidos`)
- ❌ **Cancelar Pedido** (`DELETE /pedidos/{id}`)
- 📄 **Listar Pedidos** (`GET /pedidos`)

Os dados são persistidos em um **arquivo JSON**, permitindo a simulação de uma base de dados leve e simples.

---

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Gradle**
- **Jackson (para manipulação de JSON)**
- **JUnit + Mockito (para testes unitários)**

---

## 🔧 Funcionalidades Implementadas

### ✅ `POST /pedidos`
- Efetua um novo pedido com base nos dados enviados no corpo da requisição.
- Valida os dados do cliente e produtos.
- Gera um ID único e armazena o pedido em um arquivo JSON.

### ✅ `GET /pedidos`
- Lista todos os pedidos cadastrados.
- Retorna os dados do cliente, produtos, total do pedido e status.

### ✅ `DELETE /pedidos/{id}`
- Cancela um pedido existente, alterando o status para "CANCELADO".
- Se o ID não existir, retorna erro apropriado.

---

## 🏁 Como Rodar o Projeto Localmente

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/nome-do-repositorio.git
   cd nome-do-repositorio