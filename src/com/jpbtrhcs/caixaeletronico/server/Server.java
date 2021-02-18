package com.jpbtrhcs.caixaeletronico.server;

import com.jpbtrhcs.caixaeletronico.server.service.Invoker;
import com.jpbtrhcs.caixaeletronico.util.Mensagem;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server implements Runnable{
    private Arquivo arquivo;
    private BufferedReader leitor;
    private BufferedWriter gravador;
    private HashMap<String, Conta> contas;
    private ServerSocket serverSocket;
    private Mensagem m;

    private Conta contaSAQUE;
    private int valorSAQUE;

    public Server() {}

    public Server(String path) {
        contas = new HashMap<String, Conta>();
        arquivo = new Arquivo(path);
    }

    public Server(Conta conta, int valor, HashMap<String, Conta> contas, Arquivo arquivo, BufferedWriter gravador){
        this.contaSAQUE = new Conta(conta.getNome(), conta.getSenha(), conta.getSaldo());
        this.valorSAQUE = valor;
        this.contas = contas;
        this.arquivo = arquivo;
        this.gravador = gravador;

    }

    public void setContaSAQUE(Conta contaSAQUE) {
        this.contaSAQUE = contaSAQUE;
    }

    public Conta getContaSAQUE() {
        return contaSAQUE;
    }

    public int getValorSAQUE() {
        return valorSAQUE;
    }

    public Arquivo getArquivo() {
        return arquivo;
    }

    public Mensagem getM() {
        return m;
    }

    public void setM(Mensagem m) {
        this.m = m;
    }

    public BufferedWriter getGravador() {
        return gravador;
    }

    public HashMap<String, Conta> getContas() {
        return contas;
    }

    private void criandoServerSocket(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private void tratandoConexao(Socket socket, Server app) throws IOException, ClassNotFoundException {
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

        // protocolos
        System.out.println("Tratando...");
        app.setM((Mensagem) input.readObject());
        String operacao = app.getM().getOperacao();

        Invoker protocolos = new Invoker();

        Mensagem reply = protocolos.invoke(app, operacao);
        output.writeObject(reply);
        output.flush();

        input.close();
        output.close();

        socket.close();
    }

    public void iniciarServico() throws ClassNotFoundException {

        String path = "src/com/jpbtrhcs/caixaeletronico/server/contas.txt";
        Server app = new Server(path);

        try {
            app.getArquivo().lerArquivo(app.getContas());
            criandoServerSocket(5000);
            while (true) {
                System.out.println("Aguardando conexao...");
                Socket cliente = serverSocket.accept();
                System.out.println("Cliente " + cliente.getInetAddress() + " conectado.");
                tratandoConexao(cliente, app);
            }
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }

    }

    public static void main(String[] args) {
        try{
            Server server = new Server();
            server.iniciarServico();
        }catch (ClassNotFoundException err){
            System.out.println("Erro no cast: " + err);
        }
    }

    @Override
    public void run() {
        String cliente = Thread.currentThread().getName();
        try{
            int valorSacado = sacar(cliente);
            System.out.println("[" + cliente + "] Valor sacado: " + valorSacado);
        }catch(IOException err){
            System.out.println("Erro ao ler função sacar()");
        }
    }

    public synchronized int sacar(String cliente) throws IOException {
        Conta c = null;
        if(cliente != null && getContaSAQUE() != null){
            c = new Conta(getContaSAQUE().getNome(),getContaSAQUE().getSenha(),getContaSAQUE().getSaldo());
        }else{
            System.out.println("cliente ou conta vazios");
            return -1;
        }

        if (c.getSaldo() >= getValorSAQUE()) {
            int saldoOriginal = c.getSaldo();
            try {
                System.out.println("[" + cliente + "] Processando saque, aguarde...");
                Thread.sleep(500); // tempo de processamento
            } catch (InterruptedException exception) {
                System.out.println("Error: " + exception);
            }
            c.setSaldo(c.getSaldo() - getValorSAQUE());
            System.out.println("[" + cliente + "] saque de " + getValorSAQUE() + ". [Saldo original=" + saldoOriginal + ", Saldo final=" + c.getSaldo() + "]");
            setContaSAQUE(c);
            contas.put(c.getNome(), c);
            getArquivo().salvandoArquivo(contas);
            return getValorSAQUE();
        } else {
            System.out.println("[" + cliente + "] Nao eh possivel sacar!");
            return 0;
        }
    }
}
