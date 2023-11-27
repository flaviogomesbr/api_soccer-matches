## api partidas de futebol

<div align="center">
    <h1>
    <img width=150 src='https://s2-techtudo.glbimg.com/twoewJmwpMgtGPcRPP8SxFlDVmM=/0x0:695x393/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2021/P/f/y52r4ySZWLkJjEhKLhgw/2014-11-14-java-logo.jpg'>
    <img width=150 src='https://www.qindel.com/wp-content/uploads/2023/04/spring-boot.jpeg'>
    </h1>
</div>

## Sobre

API em Java (Spring Boot) para salvar partidas de futebol, persistindo os dados em um banco de dados relacional (MySQL).

## Funcionalidades e requisitos da API

**Tecnologias**

- Java 17 ou 21
- Spring Boot
- Persistir os dados em um banco de dados relacional (MySQL, Postgres, SQLite, H2, HSQLDB, etc.)

**Operações básicas que a aplicação deverá permitir**

- cadastrar uma partida, contendo no mínimo o nome dos clubes, o resultado da partida, a data e a hora da partida e o nome do estádio;
- atualizar os dados de uma partida;
- remover uma partida do cadastro;

**Buscas**

A aplicação deverá permitir a busca por:
- partidas que terminaram em uma goleada (3 ou mais gols de diferença para um dos clubes);
- partidas que terminaram sem gols para nenhum dos clubes;
- todas as partidas de um clube específico, podendo filtrar as partidas onde este clube atuou como mandante ou como visitante;
- todas as partidas de um estádio específico;
  
**Validações**

A aplicação não deverá permitir o cadastro ou a atualização:
- de uma partida antes das 8h ou após às 22h;
- de mais de uma partida em um mesmo estádio no mesmo dia;
- de mais de uma partida de um mesmo clube com menos de dois dias de intervalo;
- de uma partida sem conter o nome dos clubes, a data e a hora da partida e o nome do estádio;
- de uma partida com a data e a hora da partida no futuro;
- de uma partida sem conter o resultado, ou com valores negativos no resultado;

## Requisitos da máquina
- [IDE Java IntelliJ ou equivalente técnico](https://www.jetbrains.com/pt-br/idea/) <br>
- [Java JDK 17](https://www.oracle.com/br/java/technologies/downloads/#java17) <br>
- [MySQL Community Server 8.0.35](https://dev.mysql.com/downloads/mysql/) <br>
- [MySQL Workbench 8.0.34](https://dev.mysql.com/downloads/workbench/) <br>
- [Post Man ou equivalente técnico](https://www.postman.com/downloads/) <br>

## Executando o projeto

Garantir a instalação dos "Requisitos da máquina" listados anteriormente;

Clonar este projeto para a sua máquina e executar o seguinte comando dentro do MySQL WorkBench (ou outro software equivalente técnico):

```sh
create database soccer_matches_db
```

Abrir e executar o projeto dentro da IDE IntelliJ (ou outro software equivalente técnico);

Dentro do software Postman, importar a seguinte collection da raiz do projeto:

`00_MELI_JavaSpringBoot_api_soccerMatches.postman_collection`

Executar as requisições do CRUD, iniciando pelo POST.

Para consultar os registros no banco de dados, dentro do MySQL Workbench, digitar o seguinte comando:

```sh
SELECT * FROM soccer_matches_db.partida;
```

**License**

[MIT](https://tldrlegal.com/license/mit-license)