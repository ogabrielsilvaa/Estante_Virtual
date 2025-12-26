# 📚 Estante Virtual API

Bem-vindo ao repositório da **Estante Virtual**, uma API RESTful robusta desenvolvida para gerenciamento pessoal de leituras. O sistema permite que usuários cataloguem livros, acompanhem seu progresso de leitura e escrevam avaliações detalhadas.

## 🚀 Sobre o Projeto

O objetivo do Estante Virtual é fornecer uma plataforma onde leitores podem organizar sua vida literária. A aplicação implementa conceitos sólidos de engenharia de software, incluindo **Design Orientado a Domínio (DDD)**, **Padrão DTO**, **Mappers** e **Segurança com JWT**.

### Principais Funcionalidades

* **Autenticação e Autorização:** Login seguro com JWT e controle de acesso baseado em Roles (Customer e Admin).
* **Catálogo de Livros:** Gestão completa de livros (CRUD) restrita a administradores.
* **Minha Estante:** Usuários podem adicionar livros à sua estante pessoal e gerenciar estados: *Quero Ler, Lendo, Lido, Abandonado*.
* **Progresso de Leitura:** Controle de páginas lidas e datas de início/fim.
* **Reviews e Avaliações:** Sistema de avaliações com notas específicas (Enredo, Personagens, Escrita, Imersão) e reviews em texto.
* **Soft Delete:** Implementação de exclusão lógica para preservação de histórico.

## 🛠 Tecnologias Utilizadas

* **Java 21+**
* **Spring Boot 3**
* **Spring Security & JWT** (Autenticação Stateless)
* **Spring Data JPA** (Persistência)
* **Maven** (Gerenciamento de dependências)
* **Swagger / OpenAPI** (Documentação viva da API)
* **Banco de Dados:** H2 (Dev) / MySQL (Prod)

## 🗂 Modelagem do Banco de Dados

Abaixo está o Diagrama de Entidade-Relacionamento (DER) que representa a estrutura do banco de dados, incluindo as tabelas `users`, `books`, `user_books` (tabela associativa da estante) e `reviews`.

![Diagrama da Modelagem do Banco de Dados]([https://placehold.co/800x400?text=Espaco+para+Imagem+do+DER](https://github.com/ogabrielsilvaa/Estante_Virtual/blob/main/Modelagem%20do%20Banco.png))

> **Nota:** A tabela `user_books` serve como o coração do sistema, ligando usuários a livros e armazenando o estado individual de leitura.

## ⚙️ Arquitetura e Padrões

O projeto segue uma arquitetura em camadas bem definida para garantir a manutenibilidade:

1.  **Controller:** Camada REST que recebe as requisições HTTP.
2.  **Service:** Contém toda a regra de negócio (validações, cálculos).
3.  **Repository:** Interface com o Banco de Dados.
4.  **Mapper:** Camada responsável por converter DTOs em Entidades e vice-versa, mantendo os Services limpos.
5.  **DTO (Data Transfer Object):** Objetos para tráfego de dados, garantindo que a Entidade do banco nunca seja exposta diretamente.

## 🚀 Como Executar

### Pré-requisitos
* Java 17 ou superior instalado.
* Maven instalado.

### Passos
1.  Clone o repositório:
    ```bash
    git clone [https://github.com/seu-usuario/estante-virtual.git](https://github.com/seu-usuario/estante-virtual.git)
    ```
2.  Entre na pasta do projeto:
    ```bash
    cd estante-virtual
    ```
3.  Execute o projeto via Maven:
    ```bash
    mvn spring-boot:run
    ```
4.  Acesse a documentação da API (Swagger):
    * `http://localhost:8080/swagger-ui.html`

---
Desenvolvido por **Gabriel Silva**
