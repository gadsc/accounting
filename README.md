# Projeto que cria e armazena lançamentos contábeis sem usar SQL

## Setup
- Para inicilizar o projeto basta ir na raiz do projeto e executar um `mvn spring-boot:run`

## Cadastro de lançamentos contábeis
Para cadastrar um lançamento contábel basta fazer um `POST` para o path `/lancamentos-contabeis` passando um JSON como o exemplo:
```json
{
	"contaContabil": 1111002,
	"data": 20170130,
	"valor": 20000.15
}
```

Ele irá retornar um `UUID` que torna esse lançamento contábel buscável, exemplo de retorno:
```json
{
    "id": "2f94944a-9502-456f-bd75-e3ca06a4d76f"
}
```

*Obs*: todos os campos são obrigatórios.

## Busca de lançamento contábel por ID
Para buscar um lançamento contábil basta fazer um `GET` para o path `/lancamentos-contabeis/${UUID}` passando o `UUID` ele irá retornar um lançamento contábil se ele existir, exemplo de retorno:
```json
{
    "contaContabil": 1111002,
    "data": 20170130,
    "valor": 20000.15
}
```

## Busca de lançamento contábel por Conta Contábil
Para buscar um lançamento contábil basta fazer um `GET` para o path `/lancamentos-contabeis?contaContabil=1111002` passando o número da conta contábil por query param ele irá retornar os lançamentos contábeis se existirem, exemplo de retorno:
```json
[
    {
        "contaContabil": 1111002,
        "data": 20170130,
        "valor": 20000.15
    },
    {
        "contaContabil": 1111002,
        "data": 20170130,
        "valor": 30000.15
    }
]
```

## Estatísticas dos lançamentos contábeis
Para retornas as estátisticas dos lançamento contábeis cadastrados basta fazer um `GET` para o path `/lancamentos-contabeis/_stats?contaContabil=1111002` passando o número da conta contábil por query param ele irá retornar as estátisticas dos lançamentos contábeis que já foram cadastrados com esse número de conta contábil, exemplo de retorno:
```json
{
    "soma": 75000.45,
    "min": 20000.15,
    "max": 30000.15,
    "media": 25000.15,
    "qtde": 3
}
```

## Estatísticas dos lançamentos contábeis pelo número da Conta Contábil
Para retornas as estátisticas dos lançamento contábeis cadastrados basta fazer um `GET` para o path `/lancamentos-contabeis/_stats` ele irá retornar as estátisticas dos lançamentos contábeis que já foram cadastrados, exemplo de retorno:
```json
{
    "soma": 50000.3,
    "min": 20000.15,
    "max": 30000.15,
    "media": 25000.15,
    "qtde": 2
}
```
