📚 Agenda Senac API
Bem-vindo ao repositório da Agenda Senac API! Este projeto é uma aplicação RESTful desenvolvida com Spring Boot, com autenticação JWT, persistência de dados usando JPA/Hibernate, e conexão a um banco de dados relacional. A API permite gerenciar disciplinas, usuários, turmas e conceitos de maneira eficiente.

🚀 Tecnologias Utilizadas
Java 17: Linguagem de programação.
Spring Boot 3.0: Framework para criação da API.
Spring Data JPA: Persistência de dados.
MySQL: Banco de dados relacional.
JWT (JSON Web Token): Autenticação e autorização.
Maven: Gerenciador de dependências.

📦 Estrutura do Projeto:
src
├── main
│   ├── java
│   │   └── com
│   │       └── agendasenac
│   │           ├── controllers   # Controladores REST
│   │           ├── models        # Entidades JPA
│   │           ├── repositories  # Interfaces de Repositório
│   │           ├── services      # Lógica de Negócio
│   │           └── componets     # Configurações JWT e outras 
|   |           └── util          # Codigos de repetições Util
│   ├── resources
│   │   ├── application.properties  # Configurações do banco de dados e Spring
│   │   └── static                  # Arquivos estáticos (CSS, JS)
└── pom.xml                         # Arquivo de dependências Maven


🛠️ Pré-requisitos
Antes de iniciar, certifique-se de ter os seguintes itens instalados em sua máquina:

Java 17
Maven 3.x
MySQL

⚙️ Configuração
1. Clone o repositório
2. git clone https://github.com/Raph03200/MedioTec-PI-ADS/edit/main/backend

3. Compilar o projeto
Na raiz do projeto, execute o seguinte comando para compilar e baixar todas as dependências:
3.1 mvn clean install


4 mvn spring-boot:run

🔒 Autenticação
A API usa JWT (JSON Web Tokens) para autenticação. Para acessar os endpoints protegidos, você precisa primeiro se autenticar e obter um token.

Rota de Login: POST /login

Request Body:

json
Copiar código
{
    "email": "usuario@example.com",
    "password": "sua-senha"
}
Response:

json
Copiar código
{
    "token": "seu.jwt.token"
}


Use esse token no cabeçalho das requisições subsequentes:
Authorization: Bearer seu.jwt.token

🛑 Possíveis Problemas
Erro de Conexão ao Banco de Dados: Certifique-se de que o Postgres está rodando e que as credenciais no application.properties estão corretas.
Falha ao Compilar Dependências: Verifique se o Maven está corretamente configurado e atualizado.


📄 Licença
Este projeto está licenciado sob a licença MIT. Veja o arquivo LICENSE para mais detalhes.


