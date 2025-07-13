# Encurtador de URL

Este projeto é um encurtador de URLs desenvolvido em Java, utilizando Spring Boot, JPA e banco de dados H2 em memória. Ele permite gerar URLs curtas para endereços originais, armazenando-os com prazo de expiração, e realizar redirecionamento para a URL original caso esteja válida.

## Funcionalidades

- **Gerar URL curta**: Recebe uma URL original e retorna uma versão encurtada.
- **Redirecionar**: Acessando a URL curta, o sistema redireciona para a URL original se ainda não estiver expirada.
- **Expiração automática**: URLs curtas expiram após 12 dias e são removidas automaticamente.

## Estrutura do Projeto

- **Model**: Define a entidade `Url` com campos para URL original, curta, e data de expiração.
- **Repository**: Interface com métodos JPA para buscar por URL curta.
- **Service**: Lógica para gerar, salvar, buscar e expirar URLs.
- **Controller**: Endpoints REST para encurtar e redirecionar URLs.

## Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- H2 Database (em memória)
- Lombok

## Como Rodar o Projeto

### 1. Pré-requisitos

- Java 17 ou superior instalado
- Maven instalado

### 2. Clonando o repositório

```bash
git clone https://github.com/JoaoPedro019/encurtador.url
cd <NOME_DA_PASTA>
```

### 3. Instalando dependências

O projeto utiliza Maven, então basta rodar:

```bash
mvn clean install
```

### 4. Executando a aplicação

```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

### 5. Acessando o console do banco H2

O console do banco de dados H2 está habilitado e pode ser acessado via:
```
http://localhost:8080/h2-console
```
Utilize:
- JDBC URL: `jdbc:h2:mem:urldb`
- Usuário: `sa`
- Senha: (em branco)

## Exemplos de Uso

### 1. Gerar URL curta

Endpoint: `POST /url/shorten`

**Request:**
```json
{
  "url": "https://www.site-exemplo.com"
}
```

**Response:**
```json
{
  "url": "https://xxx.com/AbcXyZ"
}
```

### 2. Redirecionamento

Acesse a URL curta gerada (`https://shortUrl.com/AbcXyZ`).  
O sistema irá redirecionar para a URL original (`https://www.site-exemplo.com`) se ela estiver válida e não expirada.

Caso esteja expirada ou não exista, será retornado 404.

## Explicação das Principais Classes

### Url (Model)
Representa a entidade principal do sistema, armazenando dados da URL original, curta e data de expiração.

### UrlRepository
Interface JPA para persistência e busca de URLs pelo campo `shortUrl`.

### UrlService
Contém a lógica para:
- Gerar uma URL curta aleatória
- Salvar URLs
- Verificar expiração
- Buscar URLs originais via URL curta

### UrlController
Expõe endpoints REST:
- `POST /url/shorten` para criar URLs curtas
- `GET /url/{shortUrl}` para redirecionar para a URL original

## Configuração (application.properties)

O sistema usa banco H2 em memória por padrão, facilitando testes e desenvolvimento local.  
As configurações principais estão em `src/main/resources/application.properties`.

## Observações

- O domínio retornado (`https://xxx.com/`) é fictício. Para produção, altere para o domínio real do seu projeto.
- O prazo de expiração das URLs pode ser ajustado conforme necessidade, alterando o valor em `UrlService`.

## Licença

Este projeto é apenas para fins educacionais/desafio técnico.
