# **Problema dos Filósofos - Simulação com Cliente/Servidor**

Este repositório contém uma solução para o problema clássico dos filósofos, onde cinco filósofos compartilham um conjunto de garfos e alternam entre os estados de pensar e comer. A solução é implementada em Java, com uma arquitetura cliente-servidor usando sockets.

## **Sumário**

- [Visão Geral](#visão-geral)
- [Requisitos](#requisitos)
- [Execução](#execução)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Componentes Principais](#componentes-principais)
- [Protocolo de Comunicação](#protocolo-de-comunicação)
- [Licença](#licença)

---

## **Visão Geral**

O problema dos filósofos é uma clássica questão de concorrência onde múltiplos filósofos estão sentados à volta de uma mesa, alternando entre pensar e comer. Cada filósofo precisa de dois garfos para comer, mas um garfo está compartilhado entre dois filósofos.

Este sistema é implementado com um servidor que gerencia as conexões dos filósofos e a alocação dos garfos, e vários clientes (filósofos) que se conectam ao servidor e alternam entre os estados de pensar, comer e esperar pelos garfos.

---

## **Requisitos**

- JDK 11 ou superior
- IDE ou editor de código para Java (por exemplo, IntelliJ IDEA, Eclipse)
- Conexão com a internet (se necessário para downloads de dependências ou bibliotecas externas)

---

## **Execução**

### **Passos para Executar a Solução**

1. **Compilar e Rodar o Servidor:**

   No terminal, vá até o diretório (src) onde o código do servidor está localizado e compile o arquivo `PhilosopherServer.java`.

   ```bash
   javac PhilosopherServer.java
   ```

   Em seguida, execute o servidor:

   ```bash
   java Main -

   OBS: Iniciei o Server em uma Thread separada no Main. Caso escreva Main e acontecer erro, é só preencher com o tab ao invés de escrever Main.
   ```

   O servidor ficará aguardando conexões de filósofos na porta `12345`.

2. **Compilar e Rodar o Cliente:**

   Para iniciar os filósofos (clientes), você pode rodar múltiplas instâncias do cliente. Cada filósofo será atribuído a um `ID` único.

   Compile o arquivo `PhilosopherClient.java`:

   ```bash
   javac PhilosopherClient.java
   ```

   E execute o cliente:

   ```bash
   java PhilosopherClient.java
   ```

   O cliente se conectará ao servidor e começará a alternar entre os estados de pensar e comer.

3. **Verificação:**

   O terminal do servidor exibirá os logs das requisições dos filósofos, como quando eles estão pensando, comendo ou esperando pelos garfos.

---

## **Estrutura do Projeto**

O projeto é composto por quatro principais pacotes/classes:

- **`PhilosopherServer.java`**: Implementação do servidor que gerencia os filósofos e os garfos. Ele lida com múltiplas conexões de clientes e aloca recursos.
- **`PhilosopherClient.java`**: Implementação do cliente (filósofo) que se conecta ao servidor e envia comandos para pensar, pedir garfos, comer e liberar garfos.
- **`PhilosopherHandler.java`**: Responsável por tratar as requisições de cada filósofo no servidor. Cada instância lida com um filósofo específico.
- ** `PhilosopherRecord`**: Armazena o estado de cada filósofo, incluindo informações como se ele possui os garfos, a quantidade de vezes que ele pensou e comeu, e o socket associado à conexão do filósofo.

---

## **Componentes Principais**

### **1. `PhilosopherServer`**

O servidor gerencia os filósofos e a disponibilidade dos garfos. Ele aceita conexões de clientes, atribui um `ID` único a cada filósofo e processa as mensagens de requisição de garfos. O servidor também mantém o controle dos filósofos ativos e os garfos disponíveis, garantindo que não haja deadlock ou fome eterna.

### **2. `PhilosopherClient`**

O cliente (filósofo) é responsável por se conectar ao servidor e interagir com ele, alternando entre os estados de pensar, comer e liberar garfos. O cliente realiza solicitações de garfos e, ao receber uma resposta positiva, pode começar a comer. Ele libera os garfos após comer e pode desconectar-se do servidor.

### **3. `PhilosopherHandler`**

Cada filósofo é tratado por um `PhilosopherHandler`, que é executado em uma thread separada no servidor. Este componente processa as requisições dos filósofos, como solicitações de garfos e liberação dos mesmos, e gerencia o ciclo de vida da interação de cada filósofo.

### **4. `PhilosopherRecord`**

Essa classe mantém o estado de cada filósofo, como se ele tem garfos ou não, o número de vezes que ele pensou e comeu. Ela é usada no servidor para rastrear o estado de cada filósofo conectado.

---

## **Protocolo de Comunicação**

O protocolo entre o servidor e os clientes (filósofos) segue a seguinte estrutura:

1. **Conexão Inicial**:

   - O cliente envia `HELLO` para se registrar, e o servidor responde com um `ID` único.

2. **Ciclo de Pensar**:

   - O filósofo envia `THINKING <ID>`, e o servidor responde com `OK`.

3. **Solicitação de Garfos**:

   - O filósofo envia `REQUEST_FORKS <ID>`, e o servidor responde com `FORKS_GRANTED` ou `WAIT`.

4. **Ciclo de Comer**:

   - O filósofo envia `EATING <ID>`, e o servidor responde com `OK`.

5. **Liberação de Garfos**:

   - O filósofo envia `RELEASE_FORKS <ID>`, e o servidor responde com `OK`.

6. **Desconexão**:
   - O filósofo envia `QUIT`, e o servidor responde com `BYE`.

Para mais detalhes sobre o protocolo, consulte o arquivo [PROTOCOL.md](PROTOCOL.md).

---
