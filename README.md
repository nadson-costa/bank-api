# BankAPI

API RESTful de sistema bancário desenvolvida com Spring Boot. Projeto desenvolvido com finalidade de aprofundar o estudo em Java e Spring Boot

## Tecnologias

- Java 21
- Spring Boot 3.5
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL
- Docker

## Funcionalidades

- Autenticação com JWT (registro/login)
- Gerenciamento de contas bancárias
- Transações: depósito, saque e transferência
- Extrato de transações

## Endpoints

### Autenticação
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/auth/registro` | Criar conta |
| POST | `/api/auth/login` | Autenticar |

### Contas (requer autenticação)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/contas/minha` | Buscar minha conta |
| GET | `/api/contas/{id}` | Buscar conta por ID |
| GET | `/api/contas/{id}/saldo` | Consultar saldo |
| GET | `/api/contas/{id}/extrato` | Listar transações |

### Transações (requer autenticação)
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/transacoes/deposito` | Realizar depósito |
| POST | `/api/transacoes/saque` | Realizar saque |
| POST | `/api/transacoes/transferencia` | Realizar transferência |


## Produção

 - API disponível em: https://api.nadson.dev/swagger-ui.html
 - UI da aplicação: https://bank.nadson.dev/
