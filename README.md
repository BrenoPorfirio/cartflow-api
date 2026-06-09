# 🛒 CartFlow API

A CartFlow API é um backend de e-commerce robusto construído com Spring Boot. Ela fornece uma solução estruturada para o gerenciamento de clientes, catálogo de produtos e um sistema de carrinho de compras que conta com alocação de estoque em tempo real e regras de validação estritas.

---

## 🌟 Principais Funcionalidades

* **Arquitetura Desacoplada:** Utiliza uma tabela intermediária `CartItem` para isolar completamente o estoque base da loja das quantidades individuais do carrinho de cada cliente.
* **Alocação Inteligente de Estoque:** Deduz automaticamente a quantidade de itens do estoque geral assim que eles são adicionados a um carrinho (`POST`).
* **Balanço Dinâmico de Inventário:** Recalcula e devolve itens dinamicamente ao estoque da loja quando um cliente atualiza a quantidade de um item no carrinho (`PUT`).
* **Validações Estritas (Guardrails):** Impede operações se as quantidades solicitadas excederem o estoque disponível ou se um item estiver completamente esgotado.
* **Autocomplemento de Endereço:** Integrado com a API do ViaCEP para buscar detalhes do endereço automaticamente através do CEP.

---

## 🛠️ Tecnologias Utilizadas

* **Java**
* **Spring Boot 3.x** (Spring Data JPA, Spring Web)
* **H2 Database** (Banco de dados relacional em memória)
* **Springdoc OpenAPI / Swagger UI**
* **Feign Client** (Para integração com o ViaCEP)

---

## 📋 Documentação da API & Swagger

Visualização da Interface (Swagger)
Endpoints Principais
👥 Gerenciamento de Clientes
GET /customers - Lista todos os clientes e os valores totais de seus respectivos carrinhos.

POST /customers - Registra un novo cliente (popula o endereço automaticamente via CEP).

PUT /customers/{id} - Atualiza as informações do perfil.

🛍️ Operações do Carrinho de Compras
POST /customers/{id}/products/{productId}?quantity=X - Adiciona X unidades de um produto ao carrinho do cliente, debitando do estoque da loja.

PUT /customers/{id}/products/{productId}?quantity=Y - Atualiza a quantidade do item no carrinho para Y e faz o balanço dinâmico com o estoque restante da loja.

📦 Catálogo de Produtos
POST /products - Registra um novo produto no catálogo definindo seu estoque geral inicial.

GET /products - Verifica os itens do catálogo e o estoque restante.

Assim que a aplicação estiver rodando localmente, você poderá explorar, testar e interagir com todos os endpoints diretamente através da interface interativa do Swagger UI.

## Customers
<img width="1456" height="458" alt="image" src="https://github.com/user-attachments/assets/91349bb0-3715-4232-91ca-29e6d460f6a9" />

## Products
<img width="1460" height="347" alt="image" src="https://github.com/user-attachments/assets/ed0fe309-a03c-4df1-b491-36148fd21a03" />


### URL de Acesso
```text
http://localhost:8080/swagger-ui/index.html
