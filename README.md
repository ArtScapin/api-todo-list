
# API Todo List

Projeto com o objetivo desenvolver a api de uma ferramenta versátil e acessível para que os usuários possam organizar, priorizar e acompanhar suas tarefas de forma eficiente, melhorando sua produtividade e gestão do tempo.


## Configuração de Ambiente

Para rodar esse projeto, você vai precisar fazer alguns ajustes no arquivo `src/main/resources/application.properties`:

- Ajustar endereço e porta do banco de dados;
- Adicionar nome da base de dados;
- Adicionar nome de usuário do banco de dados;
- Adicionar senha do banco de dados;

## Autenticação

Antes de acessar qualquer endpoint, é necessário autenticar-se na rota `POST /login`, fornecendo um token válido no cabeçalho `Authorization`.

Obs: Caso ainda não possua um usuário no sistema, basta cadastrar-se na rota `POST /user`.


## Documentação da API

### Login

```http
  POST /login
```

Corpo da requisição:

```json
    {
        "username": "adm",
        "password": "123"
    }
```

Exemplo de resposta:

```json
    {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJBUEkgVE9ETyBMSVNUIiwic3ViIjoiYWRtIiwiUk9MRSI6IkFETSIsImV4cCI6MTY5Njg4ODYxMn0.wr3RLy7cxxtxmJsAd7hrTniY7YldneLsHf_XYG1NpGI"
    }
```

#
### Usuários

#### Lista todos os usuários - Precisa permissões de administrador

```http
    GET /user  
```

Exemplo de resposta:

```json
    [
        {
            "id": 1,
            "username": "adm",
            "name": "Administrador",
            "permission": "ADM"
        },
        {
            "id": 2,
            "username": "Teste",
            "name": "Testinho da Silva",
            "permission": "USER"
        }
    ]
```

#
#### Lista um usuário - Precisa permissões de administrador

```http
    GET /user/{id}  
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. Identificador do usuário desejado |

Exemplo de resposta:

```json
    {
        "id": 1,
        "username": "adm",
        "name": "Administrador",
        "permission": "ADM"
    }
```

#
#### Lista as informações do usuário autenticado

```http
    GET /user/me  
```

Exemplo de resposta:

```json
    {
        "id": 1,
        "username": "adm",
        "name": "Administrador",
        "permission": "ADM"
    }
```

#
#### Cria um novo usuário

```http
    POST /user  
```

Corpo da requisição:

```json
    {
        "name":"Testinho da Silva",
        "username":"teste",
        "password":"123"
    }
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `name` | `string` | **Obrigatório**. Nome do usuário |
| `username` | `string` | **Obrigatório**. Username do usuário |
| `password` | `string` | **Obrigatório**. Senha do usuário |

Exemplo de resposta:

```json
    {
        "id": 2,
        "username": "teste",
        "name": "Testinho da Silva",
        "permission": "USER"
    }
```

#
#### Altera informações do usuário autenticado

```http
    PUT /user  
```

Corpo da requisição:

```json
    {
        "name":"Testinho",
        "password":"1234"
    }
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `name` | `string` | Novo nome do usuário |
| `password` | `string` | Nova senha do usuário |

Exemplo de resposta:

```json
    {
        "id": 2,
        "username": "teste",
        "name": "Testinho",
        "permission": "USER"
    }
```

#
#### Da permissão de administrador para um usuário - Precisa permissões de administrador

```http
    PUT /user/{id}  
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. Identificador do usuário desejado |

Exemplo de resposta:

```json
    {
        "id": 2,
        "username": "teste",
        "name": "Testinho da Silva",
        "permission": "ADM"
    }
```

#
#### Deleta o usuário autenticado

```http
    DELETE /user 
```

#
### Workspaces

#### Lista os workspaces do usuário autenticado

```http
    GET /workspace 
```

Exemplo de resposta:

```json
    [
        {
            "id": 1,
            "name": "Projeto Teste"
        },
        {
            "id": 1,
            "name": "Projeto Teste 2"
        }
    ]
```

#
#### Lista um workspace do usuário autenticado

```http
    GET /workspace/{id} 
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. Identificador do workspace desejado |

Exemplo de resposta:

```json
    {
        "id": 1,
        "name": "Projeto Teste"
    }
```

#
#### Cria um workspace para o usuário autenticado

```http
    POST /workspace
```

Corpo da requisição:

```json
    {
        "name": "Projeto Teste"
    }
```

Exemplo de resposta:

```json
    {
        "id": 1,
        "name": "Projeto Teste"
    }
```

#
#### Altera um workspace do usuário autenticado

```http
    PUT /workspace/{id} 
```

Corpo da requisição:

```json
    {
        "name": "Projeto Teste 2"
    }
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. Identificador do workspace desejado |
| `name` | `string` | **Obrigatório**. Novo nome do workspace |

Exemplo de resposta:

```json
    {
        "id": 1,
        "name": "Projeto Teste 2"
    }
```

#
#### Deleta um workspace do usuário autenticado

```http
    DELETE /workspace/{id} 
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. Identificador do workspace desejado |

#
### Listas

#### Lista as listas de um workspace do usuário autenticado

```http
    GET /list/all/{id} 
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. Identificador do workspace desejado |

Exemplo de resposta:

```json
    [
        {
            "id": 1,
            "name": "Backend",
            "status": false,
            "workspace": {
                "id": 1,
                "name": "Projeto Teste"
            }
        },
        {
            "id": 2,
            "name": "Frontend",
            "status": false,
            "workspace": {
                "id": 1,
                "name": "Projeto Teste"
            }
        }
    ]
```

#
#### Lista uma lista de um workspace do usuário autenticado

```http
    GET /list/{id} 
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. Identificador da lista desejada |

Exemplo de resposta:

```json
    {
        "id": 1,
        "name": "Backend",
        "status": false,
        "workspace": {
            "id": 1,
            "name": "Projeto Teste"
        }
    }
```

#
#### Cria uma lista em um workspace do usuário autenticado

```http
    POST /list/{id}
```

Corpo da requisição:

```json
    {
        "name": "Backend"
    }
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. Identificador do workspace desejado |
| `name` | `string` | **Obrigatório**. Nome da lista |

Exemplo de resposta:

```json
    {
        "id": 1,
        "name": "Backend",
        "status": true,
        "workspace": {
            "id": 1,
            "name": "Projeto Teste"
        }
    }
```

#
#### Altera uma lista de um workspace do usuário autenticado

```http
    PUT /list/{id}
```

Corpo da requisição:

```json
    {
        "name": "Backend 2"
    }
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. Identificador do workspace desejado |
| `name` | `string` | **Obrigatório**. Novo nome da lista |

Exemplo de resposta:

```json
    {
        "id": 1,
        "name": "Backend 2",
        "status": true,
        "workspace": {
            "id": 1,
            "name": "Projeto Teste"
        }
    }
```

#
#### Deleta uma lista de um workspace do usuário autenticado

```http
    DELETE /workspace/{id} 
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. Identificador do workspace desejado |

#
### Itens

#### Lista os itens da lista de um workspace do usuário autenticado

```http
    GET /item/all/{id} 
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. Identificador da lista desejada |

Exemplo de resposta:

```json
    [
        {
            "id": 1,
            "name": "Finalizar testes na api",
            "status": true,
            "list": {
                "id": 1,
                "name": "Backend",
                "status": false,
                "workspace": {
                    "id": 1,
                    "name": "Projeto Teste"
                }
            }
        },
        {
            "id": 2,
            "name": "Fazer migration para inserir dados de teste",
            "status": false,
            "list": {
                "id": 1,
                "name": "Backend",
                "status": false,
                "workspace": {
                    "id": 1,
                    "name": "Projeto Teste"
                }
            }
        }
    ]
```

#
#### Lista um item da lista de um workspace do usuário autenticado

```http
    GET /item/{id} 
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. Identificador do item desejado |

Exemplo de resposta:

```json
    {
        "id": 1,
        "name": "Finalizar testes na api",
        "status": true,
        "list": {
            "id": 1,
            "name": "Backend",
            "status": false,
            "workspace": {
                "id": 1,
                "name": "Projeto Teste"
            }
        }
    }
```

#
#### Cria um item em uma lista de um workspace do usuário autenticado

```http
    POST /item/{id}
```

Corpo da requisição:

```json
    {
        "name": "Finalizar testes na api"
    } 
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. Identificador da lista desejada |
| `name` | `string` | **Obrigatório**. Nome do item |

Exemplo de resposta:

```json
    {
        "id": 1,
        "name": "Finalizar testes na api",
        "status": true,
        "list": {
            "id": 1,
            "name": "Backend",
            "status": false,
            "workspace": {
                "id": 1,
                "name": "Projeto Teste"
            }
        }
    }
```

#
#### Altera um item de uma lista de um workspace do usuário autenticado

```http
    PUT /item/{id}
```

Corpo da requisição:

```json
    {
        "name": "Finalizar testes na api 2"
    } 
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. Identificador da lista desejada |
| `name` | `string` | **Obrigatório**. Novo nome do item |

Exemplo de resposta:

```json
    {
        "id": 1,
        "name": "Finalizar testes na api 2",
        "status": true,
        "list": {
            "id": 1,
            "name": "Backend",
            "status": false,
            "workspace": {
                "id": 1,
                "name": "Projeto Teste"
            }
        }
    }
```

#
#### Altera o status de um item de uma lista de um workspace do usuário autenticado

```http
    PUT /item/change-status/{id}
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. Identificador do item desejado |

Exemplo de resposta:

```json
    {
        "id": 1,
        "name": "Finalizar testes na api",
        "status": true,
        "list": {
            "id": 1,
            "name": "Backend",
            "status": true,
            "workspace": {
                "id": 1,
                "name": "Projeto Teste"
            }
        }
    }
```

#
#### Deleta um item de uma lista de um workspace do usuário autenticado

```http
    DELETE /item/{id} 
```

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `id` | `int` | **Obrigatório**. Identificador do item desejado |