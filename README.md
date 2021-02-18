<div align = "center">
   <h1>Projeto Caixa Eletrônico</h1>
   <h2>Criação de socket e protocolo de aplicação Client-Servidor que simula um Caixa Eletrônico</h2>
   <img height= "750" width = "750" src = "https://github.com/ruancorrea/Caixa_Eletronico/blob/rfct/src/com/jpbtrhcs/caixaeletronico/assets/capa.png?raw=true">
   </img>
</div>

<div align = "left" >
   <p>Disciplina Redes de Computadores<br/>
   Professor Leandro Sales<br/>
   Curso Engenharia de Computação<br/>
   Alunos: &emsp;&emsp;&emsp;&emsp;<a href = "https://github.com/joaopedrobritot" target="_blank" >Joao Pedro Brito Tomé</a><br/>
      &emsp;&emsp;&emsp;&emsp;<a href = "https://github.com/ruancorrea" target="_blank" >Ruan Heleno Correa da Silva</a></p>
</div>

### Ambiente e versões


#### javac 
```javac 11.0.10```

#### jdk
```openjdk version "11.0.10" 2021-01-19```

<p>Foram utilizados no ambiente de desenvolvimento a IDE do VS Code configurado com as extensões JAVA da Microsoft e também o software do Intellij. <b>Clicamos no botão de execução(disponível em ambas IDE) para rodar o programa.</b></p>

## Introdução

<p>Neste documento há a especificação de um protocolo da camada de aplicação com arquitetura Cliente-Servidor para simular funcionalidades de um caixa eletrônico numa implementação multi-thread em que podem ser abertas várias guias de terminal representando os clientes, desde que o servidor seja executado primeiro. Oferecendo a transferência de dados de forma confiável (diferentemente da UDP), o protocolo TCP foi o escolhido para a da aplicação rodar. A porta utilizada pelo protocolo é a <b>5000</b>. </br>

<b>Porta padrão:</b> 5000 <br/>
<b>Arquitetura:</b> Cliente-Servidor <br/>
<b>Protocolo na camada de transporte:</b> TCP <br/>
<b>Padrão de caracteres:</b> ASCII <br/>
</p>

## Cliente-Servidor

<p>A arquitetura utilizada neste protocolo é a Cliente-Servidor. Desse modo, para o cliente possa utilizar as funcionalidades disponíveis é necessário que o servidor já esteja rodando e aguardando conexão entre Sockets e a partir disso a conexão entre os dois possa ser estabelecida e a interação das funcionalidades seja possibilitada através do terminal.</p>

### Cliente

<p>A função básica do cliente é interagir com as funcionalidades presentes no terminal. Navegar entre menus, inserir senhas, entre outras formas. Inicialmente é pedido que informe o nome do cliente. Depois aparece um menu contendo as funcionalidades principais da aplicação. Dependendo da escolha das funcionalidades, o cliente é levado para outro menu de login (podendo ser requirido o nome da conta ou o menu e a senha da conta na mesma linha separados por uma vírgula). </p>

<div align = "center" >

Funcionalidades | Finalidade
----------------|--------------
Sacar | para saque de dinheiro de uma determinada conta
Depositar | para deposito de dinheiro de uma determinada conta
Abrir conta | para abertura de uma nova conta
Sair | finalizar a execução do cliente

</div>

### Servidor

<p>As funções básicas do servidor são a de aceitar conexões de múltiplos clientes, aceitar os comandos e armazenar e manipular os dados enviados pelos clientes. As funções são possibilitadas após a leitura de um arquivo chamado <b>"contas.txt"</b> presente na pasta do servidor, no qual está presente todas as informações das contas abertas. São três informações para cada conta, nome, senha e saldo, e no arquivo cada informação é separada por uma vírgula, e a cada quebra de linha equivale a uma conta aberta.
Além das classes <i>ServerSocket</i> e <i>Socket</i> que cuidam, respectivamente, do Servidor e do Cliente.</p>

#### Protocolos

<p>Tem o papel de definir os padrões a serem utilizados para o envio, o recebimento e o tratamento de dados enviados pela rede.</br>
Os protocolos adicionados para requisições são:</p>

<div align="center" >

Protocolos| Função 
----------|--------
LOGINSENHA|antes de <b>sacar</b> terá um login onde é requerido a <b>nome</b> e a <b>senha</b> da conta.
LOGINNOME|antes de <b>depositar</b> terá um login onde é requerido a <b>nome</b> da conta
SAQUE| para <b>saque</b> de dinheiro de determinada conta.
DEPOSITO| para <b>deposito</b> de dinheiro de determinada conta.
CRIARCONTA|para <b>abrir conta</b>.

</div>

## Sockets

<p>Os sockets presentes na aplicação são baseados em TCP, onde utilizados as classes <i>ObjectInputStream</i> e <i>ObjectOutputStream</i> para tal troca de dados entre o cliente e servidor pela rede. </p>

### Cliente

<h4><i>Criação de Socket</i></h4>

```java
public void criandoSocket() throws IOException {
    socket = new Socket("127.0.0.1", 5000);
    System.out.println("Conectado com o servidor.");
    output = new ObjectOutputStream(socket.getOutputStream());
    input = new ObjectInputStream(socket.getInputStream());
}
```

<h4><i>Finalização de Socket </i></h4>

```java
public void finalizandoSocket() throws IOException {
    input.close();
    output.close();
    socket.close();
    System.out.println("Conexao com o servidor finalizada.");
}
```

### Servidor

<h4><i>Criação de ServerSocket</i></h4>

```java
private void criandoServerSocket(int port) throws IOException {
    serverSocket = new ServerSocket(port);
}
```

<p><i>O servidor continua rodando em um loop infinito até que o desenvolvedor finalize a execução. Cada requisição do cliente com o servidor é criada uma conexão e logo depois da troca de dados a conexão entre os dois é encerrada.</i></p>

```java
private void tratandoConexao(Socket socket, Server app) throws IOException, ClassNotFoundException {
    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
    // protocolos [...]
    Mensagem reply = protocolos.invoke(app, operacao);
    output.writeObject(reply);
    output.flush();

    input.close();
    output.close();

    socket.close();
}
```

## Threads

<p>As Threads estão sendo utilizadas ao sacar dinheiro de determinada conta. É implementada na classe servidor a interface <i>Runnable</i>, com o método <i>run()</i>, esse método é sempre iniciado quando uma instância <b>Thread </b> chama o método <i>start()</i>, e é a partir de <i>run()</i> que o subprocesso é iniciado. Para tal saque temos o método <i>sacar()</i> que está definido como <i>synchronized</i> garantindo uma organização "um de cada vez" para saques simultâneos impossibilitando assim que em tais saques simultâneos o dinheiro sacado seja maior que o saldo de tal conta. </p> 

<h4><i>Interface Runnable implementada na classe Server</i></h4>

```java
public class Server implements Runnable{
  // [...]
}
```
<h4><i>A interface Runnable carrega consigo o método run() e assim o mesmo é trazido para a classe Server</i></h4>

```java
@Override
public void run() {
  // [...]
}
```
<p><i>Na classe do protocolo de Saque quando o mesmo é chamado para execução é instânciado uma Thread que carrega consigo uma pequena copia da instância principal do Servidor e uma string com o nome do cliente da conta. Na mesma linha é chamado a classe start() que inicia a execução da Thread no método run().</i></p>

```java
new Thread(serverCOPY, nome).start();
```

<p><i>O método sacar() é chamada no método run() e através dele é feito o saque do dinheiro da conta. Note que o mesmo traz consigo "synchronized" para organizar saques simultanêos na mesma conta.</i></p>

```java
public synchronized int sacar(String cliente) throws IOException {
  // [...]
}
```

## Padrões de Projeto utilizados

<p>Gerindo o comportamento do programa tanto no lado do servidor, quanto do cliente, temos a presença de dois padrões de projeto: </p>

Lado| Padrão
-----|-----
Servidor | Command
Cliente |Strategy

<p>Enquanto no lado do cliente o padrão Strategy gere a navegação pelas funcionalidades, no lado do servidor o padrão Command cuida da execução dos protocolos que devem ser chamados pela determinada funcionalidade escolhida pelo cliente. </p>
