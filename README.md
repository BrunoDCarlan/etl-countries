# Projeto ETL - MDM Countries

Este projeto representa o módulo de ETL (Extract, Transform, Load) responsável por extrair dados da API pública [REST Countries](https://restcountries.com/v3.1/all), processá-los e salvá-los em arquivos JSON localmente. Posteriormente, os dados são enviados ao sistema MDM (Master Data Management).

## Funcionalidades

- Consulta de todos os países via API REST Countries
- Salvamento do resultado bruto (`raw.json`)
- Divisão em múltiplos arquivos de 50 países por chunk
- Contagem de registros no arquivo bruto
- Transformação dos dados para o formato aceito pelo MDM
- Envio de dados ao MDM via API REST

## Endpoints

| Método | Rota                         | Descrição                                    |
|--------|------------------------------|----------------------------------------------|
| GET    | `/etl/download/{providerId}` | Realiza o download e armazenamento dos dados |
| GET    | `/etl/load`                  | Lê os arquivos locais e envia ao MDM         |
| GET    | `/etl/downloads`             | Retorna a lista de downloads                 |
| GET    | `/etl/providers`             | Retorna a lista de providers                 |
| GET    | `/etl/providers/{id}`        | Retorna o provider especificado              |
| POST   | `/etl/providers`             | Cadastra um novo provider                    |
| PUT    | `/etl/providers/{id}`        | Atualiza o provider especificado             |
| DELETE | `/etl/providers/{id}`        | Deleta o provider especificado               |

## Estrutura de Diretórios

```
etl/
├── controller/
│   └── EtlController.java
|   └── ProviderController.java
├── dto/
│   └── MdmCountryDTO.java
│   └── MdmCurrencyDTO.java
├── exception/
│   └── GlobalExceptionHandler.java
├── mapper/
│   └── EtlToMdmTransformer.java
├── model/
│   └── CapitalInfo.java
│   └── Country.java
│   └── Download.java
│   └── Name.java
│   └── Provider.java
├── repository/
│   └── DownloadRepository.java
│   └── ProviderRepository.java
├── service/
│   └── EtlService.java
│   └── ProviderService.java
└── EtlApplication.java
```

## Estrutura de Dados Enviada ao MDM

### Exemplo de JSON enviado:
```json
{
  "countryName": "Brazil",
  "numericCode": 076,
  "capitalCity": "Brasília",
  "population": 211000000,
  "area": 8515767.0,
  "currencies": [
    {
      "currencyCode": "BRL",
      "currencyName": "Brazilian real",
      "currencySymbol": "R$"
    }
  ]
}
```

### Exemplo de JSON para cadastrar provider:
```json
{
  "providerName": "Provider_name"
}
```

## Dependências

- Spring Boot
- Jackson
- RestTemplate
- Spring Data JPA
- PostgreSQL Driver
- Lombok
- Hibernate

## Execução

1. Suba o MDM (porta 8080)
2. Inicie este projeto (porta 8081)
3. Cadastre um provider `http://localhost:8081/etl/providers` com JSON informando nome
4. Acesse `http://localhost:8081/etl/download` para baixar e salvar os dados
5. Acesse `http://localhost:8081/etl/load` para enviá-los ao MDM

