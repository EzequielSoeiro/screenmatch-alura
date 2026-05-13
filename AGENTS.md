# AGENTS.md

## Visão Geral
Este projeto é uma aplicação Java usando Spring Boot e Spring Data JPA, organizada em uma arquitetura em camadas. Integra APIs externas (OMDb para dados de séries, MyMemory para tradução) e persiste dados usando PostgreSQL via repositórios JPA.

## Arquitetura & Fluxo de Dados
- **Ponto de Entrada:** `principal/Principal.java` orquestra a interação com o usuário, lógica de menu e coordena chamadas de serviço/repositório.
- **Integração de API:** `service/ConsumoApi.java` busca JSON do OMDb; `service/ConverteDados.java` converte JSON em DTOs (records Java como `DadosSerie`).
- **Tradução:** `service/traducao/ConsultaMyMemory.java` integra com a API MyMemory para traduzir textos (ex: sinopses) durante a ingestão dos dados.
- **Persistência:** Entidades (`model/Serie.java`, `model/Episodio.java`) são mapeadas a partir dos DTOs e persistidas via repositórios (`repository/SerieRepository.java`, `EpisodioRepository.java`).
- **Repositórios:** Usam tanto convenções do Spring Data quanto anotações `@Query` para consultas complexas.

## Padrões & Convenções
- **DTOs como Records:** Respostas de APIs são mapeadas para records (ex: `DadosSerie`) para transferência de dados concisa e imutável.
- **Mapeamento via Construtor:** Entidades são construídas a partir de DTOs, frequentemente com tradução aplicada na criação.
- **Serviços Stateless:** Classes de serviço são utilitárias e sem estado.
- **Abstração de Repositório:** Todo acesso ao banco é via interfaces de repositório, nunca SQL direto na lógica de negócio.
- **Tradução na Ingestão:** Campos traduzidos são armazenados no banco, não traduzidos sob demanda.

## Fluxos do Desenvolvedor
- **Configuração do Projeto:**
  - Após clonar, clique com o botão direito no `pom.xml` e selecione "Add as Maven Project" no IntelliJ.
- **Build & Execução:**
  - Use Maven (`mvnw.cmd` no Windows) para buildar e rodar o projeto.
- **Banco de Dados:**
  - Configuração padrão usa PostgreSQL; veja `application.properties` para detalhes.
- **Testes:**
  - Testes estão em `src/test/java/...` (expanda conforme necessário).

## Arquivos & Diretórios-Chave
- `principal/Principal.java` — Lógica principal, menu, orquestração
- `service/ConsumoApi.java`, `ConverteDados.java` — Cliente de API e parsing JSON
- `service/traducao/ConsultaMyMemory.java` — Integração com API de tradução
- `model/Serie.java`, `Episodio.java` — Entidades JPA
- `repository/SerieRepository.java`, `EpisodioRepository.java` — Acesso a dados

## Pontos de Integração
- **OMDb API:** Para dados de séries/temporadas/episódios (via `ConsumoApi`)
- **MyMemory API:** Para tradução (via `ConsultaMyMemory`)
- **PostgreSQL:** Para armazenamento persistente (via repositórios JPA)

## Exemplo de Fluxo de Dados
1. Usuário seleciona uma opção em `Principal.java`.
2. Dados buscados do OMDb via `ConsumoApi`.
3. JSON convertido em DTOs com `ConverteDados`.
4. DTOs mapeados para entidades, tradução aplicada.
5. Entidades persistidas via repositórios.
6. Consultas e resultados apresentados ao usuário.

---
Para mais detalhes, veja os arquivos e estrutura de pacotes em `screenmatch/src/main/java/br/com/alura/screenmatch/`.
