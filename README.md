<div align = "center">
   <h1>Projeto Caixa Eletrônico</h1>
   <h2>Criação de socket e protocolo de aplicação Client-Servidor que simula um Caixa Eletrônico</h2>
   <img height= "750" width = "750" src = "https://github.com/ruancorrea/Caixa_Eletronico/blob/main/src/caixaEletronico/assets/capa.png?raw=true">
   </img>
</div>

<div align = "left" >
   <p>Disciplina Redes de Computadores<br/>
   Professor Leandro Sales<br/>
   Curso Engenharia de Computação<br/>
   Alunos:<br>
    &emsp;&emsp;&emsp;&emsp;<a href = "https://github.com/joaopedrobritot" target="_blank" >Joao Pedro Brito Tomé</a><br/>
    &emsp;&emsp;&emsp;&emsp;<a href = "https://github.com/ruancorrea" target="_blank" >Ruan Heleno Correa da Silva</a></p>
</div>

### Ambientes e versões

O projeto foi feito em java contendo:

#### javac
```javac 11.0.10```

#### jdk
```openjdk version "11.0.10" 2021-01-19```


<p>Foi utilizado a IDE do Intellij IDEA como ambiente de desenvolvimento e até por isso a recomendados para rodar o projeto.</p>

### Como executar

<p>Clonando repositório</p>

```$ git clone <link repositorio>```

```$ cd Caixa_Eletronico```

#### IDE Intellij(recomendado)

<p>Caso utilize a IDE Intellij, então abra o software no diretório do projeto. Edite as configurações para criar a execução do servidor e do(s) cliente(s)</p>

<div align = "center">
   <img height= "600" width = "750" src = "https://github.com/ruancorrea/Caixa_Eletronico/blob/granFinally/src/caixaEletronico/assets/servidor1_Intellij.png?raw=true">
   </img>
   <p>Configure para poder executar o servidor e, depois, o cliente.</p>
</div>

<div align = "center">
   <img height= "250" width = "750" src = "https://github.com/ruancorrea/Caixa_Eletronico/blob/granFinally/src/caixaEletronico/assets/servidor2_Intellij.png?raw=true">
   </img>
   <p>Clique no botão "Run" para executar.</p>
</div>

<p>Na classe Servidor, presente em <i>Caixa_Eletronico/src/caixaEletronico/server</i>, verifique se o caminho do arquivo(variável path) é <b>"src/caixaEletronico/server/contas.txt"</b> </p>

```java
public class Servidor{
    private Arquivo arquivo;
    private HashMap<String, Conta> contas;
    private ServerSocket serverSocket;
    private String path = "src/caixaEletronico/server/contas.txt";
}
```


<div align = "center">
   <img height= "600" width = "750" src = "https://github.com/ruancorrea/Caixa_Eletronico/blob/granFinally/src/caixaEletronico/assets/servidor3_Intellij.png?raw=true">
   </img>
   <p>Servidor em execução.</p>
</div>

<div align = "center">
   <img height= "600" width = "750" src = "https://github.com/ruancorrea/Caixa_Eletronico/blob/granFinally/src/caixaEletronico/assets/cliente1_Intellij.png?raw=true">
   </img>
   <p>Cliente em execução.</p>
</div>


#### VS Code

<p>Caso esteja utilizando o VS Code, é necessário instalar as extensões do Java, da Microsoft e a extensão Code Runner, e depois rodar em "Run Java"</p>

<p>Na classe Servidor, presente em <i>Caixa_Eletronico/src/caixaEletronico/server</i>, verifique se o caminho do arquivo(variável path) é <b>"Caixa_Eletronico/src/caixaEletronico/server/contas.txt"</b> </p>

## Introdução

<p>Neste documento há a especificação de um protocolo da camada de aplicação com arquitetura Cliente-Servidor para simular funcionalidades de um caixa eletrônico numa implementação multi-thread em que podem ser abertas várias guias de terminal representando os clientes, desde que o servidor seja executado primeiro. Oferecendo a transferência de dados de forma confiável (diferentemente da UDP), o protocolo TCP foi o escolhido para a da aplicação rodar. A porta utilizada pelo protocolo é a <b>5000</b>. </br>

<b>Porta padrão:</b> 5000 <br/>
<b>Arquitetura de aplicação:</b> Cliente-Servidor <br/>
<b>Protocolo na camada de transporte:</b> TCP <br/>
<b>Padrão de caracteres:</b> ASCII <br/>
</p>

## Cliente-Servidor

<p>A arquitetura de aplicação utilizada é a Cliente-Servidor. Desse modo, para o cliente possa utilizar as funcionalidades disponíveis é necessário que o servidor já esteja rodando e aguardando conexão entre Sockets e a partir disso a conexão entre os dois possa ser estabelecida e a interação das funcionalidades seja possibilitada.</p>

### Cliente

<p>A função básica do cliente é interagir com as funcionalidades presentes no terminal. Navegar entre menus, inserir senhas, entre outras formas. Ao iniciar o lado do cliente aparecerá um menu contendo as funcionalidades principais da aplicação. Dependendo da escolha das funcionalidades, o cliente é levado para outro menu de login (podendo ser requirido o nome da conta(depósito) ou o nome e a senha(saque) da conta na mesma linha separados por uma vírgula). O menu de login não aparecerá caso a opção seja para criar uma conta. </p>

<div align = "center" >

Funcionalidades | Finalidade
----------------|--------------
Sacar | para saque de dinheiro de uma determinada conta
Depositar | para deposito de dinheiro de uma determinada conta
Abrir conta | para abertura de uma nova conta
Sair | finalizar a execução do cliente

</div>

### Servidor

<p>As funções básicas do servidor são as de aceitar conexões de múltiplos clientes, aceitar os comandos e armazenar e manipular os dados enviados pelos clientes. As funções são possibilitadas após a leitura de um arquivo chamado </a><b>"contas.txt"</b> que está na pasta do servidor, no qual está presente todas as informações das contas abertas. São três informações para cada conta, nome, senha e saldo, e no arquivo cada informação é separada por uma vírgula, e a cada quebra de linha equivale a uma conta aberta.
Além das classes <i>ServerSocket</i> e <i>Socket</i> que cuidam, respectivamente, do Servidor e do Cliente.</p>

#### Protocolos

<p>Tem o papel de definir os padrões a serem utilizados para o envio, o recebimento e o tratamento de dados enviados.</br>
Utilizamos o protocolo de transporte TCP. 

#### Mensagens

<p>Criamos a classe Mensagem que organiza o formato das mensagens trocadas entre o lado do cliente e o lado do Servidor. Tem variaveis como o status da operação, qual operação está querendo ser feita e uma hash que armazena os possiveis parâmetros.</p>

#### Status

Os status são:</p>

<div align="center" >

Status| Significado
----------|--------
200|OK
373|SOLICITAÇÃO
400|ERROR
411|PARAMERROR
</div>

#### Operações 

<p>As operações para requisições são:</p>

<div align="center" >

Operação| Função
----------|--------
LOGINSENHA|antes de <b>sacar</b> terá um login onde é requerido o <b>nome</b> e a <b>senha</b> da conta.
LOGINNOME|antes de <b>depositar</b> terá um login onde é requerido o <b>nome</b> da conta
SAQUE| para <b>saque</b> de dinheiro de determinada conta.
DEPOSITO| para <b>depósito</b> de dinheiro de determinada conta.
CRIARCONTA|para <b>abrir conta</b>.

</div>

## Sockets

<p>Os sockets presentes na aplicação são baseados em TCP, onde utilizamos as classes <i>ObjectInputStream</i> e <i>ObjectOutputStream</i> para tal troca de dados entre o cliente e servidor. </p>

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

<p><i>O servidor continua rodando em um loop infinito até que o desenvolvedor finalize a execução.</i></p>

```java
public void iniciarServico() throws ClassNotFoundException {
        try {
            //[...]
            criandoServerSocket(5000);
            while (true){
                System.out.println("Aguardando conexao...");
                Socket cliente=serverSocket.accept();
                //[...]
            }
        } catch (IOException exception) {
                System.err.println(exception.getMessage());
        }
    }
```

## Threads

<p><i>Cada requisição do cliente com o servidor é criada uma thread para tratar tal conexão e logo depois da troca de dados a conexão entre os dois é encerrada.</i></p>

<h4><i>Interface Runnable implementada na classe ClientThread</i></h4>

```java
public class ClientThread implements Runnable{
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

<p><i>Na função iniciarServico() presente na classe Servidor, temos a criação e a chamada da Thread.</i></p>

```java
public void iniciarServico() throws ClassNotFoundException {
        try {
        //[...]
            while (true) {
                System.out.println("Aguardando conexao...");
                Socket cliente = serverSocket.accept();
                System.out.println("Cliente " + cliente.getInetAddress() + " conectado.");
                new Thread(new ClientThread(cliente, arquivo, contas)).start();
            }
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }
```

## Dificuldades encontradas

<p><b>Uso das threads</b> - inicialmente tratamos de incluir threads apenas na funcionalidade de saque, para que a cada saque fosse criado uma thread.</p>
<p><b>Uso do envio erecebimento de dados Stream (inputStream e outputStream)</b> - Houve um tempo para entendermos bem do uso do Stream e seus tipos.</p>
<p>De certa forma, criamos uma classe para tratamento dos dados, chamada Mensagem,
que tem uma hash que age dinamicamente na adição de parametros.</p>
<p><b>Controle do saque</b> - O controle do saque tem a ver com o saque simultaneo e na proteção do dinheiro de cada um.</p>
<p>Antes, como dito acima, criavamos uma thread para cada ato de sacar, mas depois vimos que tornar a criação da thread a cada requisição do cliente seria algo interessante, dessa forma criamos o clientThread, utilizando o exemplo disponibilizado pelo professor, e também "syncronized" para o controle de saques simultaneos da mesma conta.</p>

## Ideias
<p>Criação de uma API conectada a um banco de dados online(podendo utilizar nodeJS e mongoDB).</p>
<p>Adição de algumas funcionalidades - edição de dados, exclusão de contas, help(para duvidas)...<p>
<p>Aumentar segurança para proteção dos dados de cada conta.</p>


## Padrões de Projeto utilizados

<p>Gerindo o comportamento do programa tanto no lado do servidor, quanto do cliente, temos a presença de dois padrões de projeto: </p>

Lado| Padrão
-----|-----
Servidor | Command
Cliente |Strategy

<p>Enquanto no lado do cliente o padrão Strategy gere a navegação pelas funcionalidades, no lado do servidor o padrão Command cuida da execução dos protocolos que devem ser chamados pela determinada funcionalidade escolhida pelo cliente. </p>
