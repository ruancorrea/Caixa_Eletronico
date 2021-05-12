package caixaEletronico.server.service.clientThread;

import caixaEletronico.server.Arquivo;
import caixaEletronico.server.Conta;
import caixaEletronico.server.service.Invoker;
import caixaEletronico.util.Mensagem;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ClientThread implements Runnable{
    private Arquivo arquivo;
    private HashMap<String, Conta> contas;
    private Socket socket;

    public ClientThread(Socket socket, Arquivo arquivo, HashMap<String, Conta> contas){
        this.socket = socket;
        this.arquivo = arquivo;
        this.contas = contas;
    }

    private void tratandoConexao(Socket socket) throws IOException, ClassNotFoundException {
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

        // operações
        System.out.println("Tratando...");
        Mensagem mensagem = (Mensagem) input.readObject();
        System.out.println("\nInformacoes de resposta:" + socket.getInetAddress());
        System.out.println("Status: " + mensagem.getStatus() );
        String operacao = mensagem.getOperacao();
        System.out.println("Operação: " + operacao);
        Invoker operacoes = new Invoker();

        Mensagem reply = operacoes.invoke(operacao,arquivo,contas,mensagem);
        output.writeObject(reply);
        System.out.println(reply);
        output.flush();

        input.close();
        output.close();

        socket.close();
    }

    @Override
    public void run() {
        try {
            tratandoConexao(socket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
