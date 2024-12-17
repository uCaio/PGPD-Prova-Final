
# PROTOCOLO DE COMUNICAÇÃO: FILÓSOFOS E SERVIDOR

## Visão Geral
Este documento descreve o protocolo de comunicação entre os clientes (filósofos) e o servidor no contexto da solução do problema dos filósofos. Os filósofos são representados por clientes que enviam mensagens ao servidor, e o servidor gerencia os recursos (garfos) e a ordem de acesso a eles.

## Componentes do Sistema
- **Filósofo (Cliente)**: Cada filósofo é um cliente que se conecta ao servidor e alterna entre os estados de pensar, comer e esperar por garfos.
- **Servidor**: O servidor gerencia as requisições dos filósofos e aloca os garfos.

## Protocolos de Mensagens

### 1. Conexão Inicial
- **Cliente envia**: HELLO
- **Servidor responde**: HI <ID>
  
O cliente envia uma mensagem HELLO ao servidor para se identificar. O servidor atribui um ID único ao filósofo e responde com HI <ID>, onde <ID> é o identificador do filósofo.

### 2. Ciclo de Pensar
- **Cliente envia**: THINKING <ID>
- **Servidor responde**: OK

Quando o filósofo começa a pensar, ele envia uma mensagem THINKING <ID>. O servidor confirma com OK, indicando que o filósofo pode continuar pensando.

### 3. Solicitação de Garfos
- **Cliente envia**: REQUEST_FORKS <ID>
- **Servidor responde**:
  - Se os garfos estão disponíveis: FORKS_GRANTED
  - Se os garfos não estão disponíveis: WAIT

O filósofo envia uma mensagem para solicitar os garfos com REQUEST_FORKS <ID>. O servidor pode conceder os garfos com FORKS_GRANTED ou informar que o filósofo deve esperar com WAIT.

### 4. Comer
- **Cliente envia**: EATING <ID>
- **Servidor responde**: OK

Quando o filósofo começa a comer, ele envia EATING <ID>. O servidor responde com OK, permitindo que o filósofo continue a comer.

### 5. Liberação de Garfos
- **Cliente envia**: RELEASE_FORKS <ID>
- **Servidor responde**: OK

Após terminar de comer, o filósofo libera os garfos com a mensagem RELEASE_FORKS <ID>. O servidor confirma com OK, indicando que os garfos foram liberados.

### 6. Desconexão
- **Cliente envia**: QUIT
- **Servidor responde**: BYE

Quando o filósofo decide terminar a interação com o servidor, ele envia a mensagem QUIT. O servidor responde com BYE, encerrando a conexão de forma limpa.

## Exemplos de Fluxos de Comunicação

### Exemplo 1: Filosofando e Comendo
**Filosofando:**
- Cliente: THINKING 1
- Servidor: OK

**Solicitação de Garfos:**
- Cliente: REQUEST_FORKS 1
- Servidor: FORKS_GRANTED

**Comendo:**
- Cliente: EATING 1
- Servidor: OK

**Liberando Garfos:**
- Cliente: RELEASE_FORKS 1
- Servidor: OK

### Exemplo 2: Esperando pelos Garfos
**Filosofando:**
- Cliente: THINKING 2
- Servidor: OK

**Solicitação de Garfos:**
- Cliente: REQUEST_FORKS 2
- Servidor: WAIT

**Filosofando novamente:**
- Cliente: THINKING 2
- Servidor: OK

**Solicitação de Garfos (de novo):**
- Cliente: REQUEST_FORKS 2
- Servidor: FORKS_GRANTED

## Notas Importantes

### Reconexão
Se um filósofo se desconectar e tentar se reconectar, ele envia a mensagem RECONNECT <ID>, e o servidor o reconecta ao estado anterior com o mesmo ID.

### Desconexão abrupta
Se o servidor detectar que um filósofo se desconectou sem enviar a mensagem QUIT, ele deve liberar os garfos que o filósofo estava utilizando.

Este protocolo define a comunicação básica entre o servidor e os clientes (filósofos). Qualquer outra interação ou comportamento adicional pode ser adicionado conforme necessário para atender a requisitos específicos do sistema.

Este protocolo é flexível e pode ser expandido conforme necessário para adicionar mais funcionalidades, como monitoramento do estado dos filósofos ou tratamentos de erros mais complexos.
