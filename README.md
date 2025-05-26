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

| Método | Rota             | Descrição                                |
|--------|------------------|------------------------------------------|
| GET    | `/etl/start`     | Realiza o download e armazenamento dos dados |
| GET    | `/etl/load`      | Lê os arquivos locais e envia ao MDM     |

## Estrutura de Diretórios

```
etl/
├── controller/
│   └── EtlController.java
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
│   └── Name.java
├── service/
│   └── EtlService.java
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
## Dependências

- Spring Boot
- Jackson
- RestTemplate

## Execução

1. Suba o MDM (porta 8080)
2. Inicie este projeto (porta 8081)
3. Acesse `http://localhost:8081/etl/start` para baixar e salvar os dados
4. Acesse `http://localhost:8081/etl/load` para enviá-los ao MDM

