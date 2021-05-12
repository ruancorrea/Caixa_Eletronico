package caixaEletronico.server;

import caixaEletronico.server.service.clientThread.ClientThread;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Servidor{
    private Arquivo arquivo;
    private HashMap<String, Conta> contas;
    private ServerSocket serverSocket;
    private String path = "src/caixaEletronico/server/contas.txt";


    public Servidor() {
        this.contas = new HashMap<String, Conta>();
        this.arquivo = new Arquivo(path);
    }

    public Arquivo getArquivo() {
        return arquivo;
    }

    private void criandoServerSocket(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void iniciarServico() throws ClassNotFoundException {
        try {
            getArquivo().lerArquivo(contas);
            criandoServerSocket(5000);
            while (true) {
                System.out.println("Aguardando conexao...");
                Socket cliente = serverSocket.accept();
                System.out.println("\n\nCliente " + cliente.getInetAddress() + " conectado.");
                new Thread(new ClientThread(cliente, arquivo, contas)).start();
            }
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    public static void main(String[] args) {
        try{
            Servidor server = new Servidor();
            server.iniciarServico();
        }catch (ClassNotFoundException err){
            System.out.println("Erro no cast: " + err);
        }
    }
}