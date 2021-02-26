package com.jpbtrhcs.caixaeletronico.client;

import com.jpbtrhcs.caixaeletronico.client.service.OpcaoMenu;
import com.jpbtrhcs.caixaeletronico.client.service.TipoOpcaoMenu;

import java.io.IOException;
import java.util.InputMismatchException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Scanner entrada;
    public String nome;

    public Socket socket;
    public ObjectOutputStream output;
    public ObjectInputStream input;

    public Client(){
        entrada = new Scanner(System.in);
        //System.out.print("Digite seu nome\n>> ");
        //this.nome = newString();
    }

    public String getNome()
    {
        return this.nome;
    }

    public String newString(){
        return entrada.next();
    }

    public int newInt() {
        int valor;
        while(true)
        {
            try{
                valor = entrada.nextInt();
                break;
            }
            catch(InputMismatchException e){
                printf("\nO valor inserido não é um número inteiro!\nTente novamente: ")
            }
        }
        
        return valor;
    }

    public void criandoSocket() throws IOException {
        socket = new Socket("127.0.0.1", 5000);
        System.out.println("Conectado com o servidor.");
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    public void finalizandoSocket() throws IOException {
        input.close();
        output.close();
        socket.close();
        System.out.println("Conexao com o servidor finalizada.");
    }

    public void iniciar() throws IOException, ClassNotFoundException {
        System.out.print("(1) sacar , (2) depositar, (3) abrir conta, (4) finalizar\n>> ");
        int opcao = newInt();
        if(opcao > 0 && opcao < 4){
            TipoOpcaoMenu tipoOpcaoMenu = TipoOpcaoMenu.values()[opcao - 1];
            OpcaoMenu opcaoMenu = tipoOpcaoMenu.obterOpcaoMenu();
            opcaoMenu.opcaoEscolhida(new Client());
        }
        else if(opcao == 4 ){
            System.out.println("Sessão finalizada.");
        }
        else{
            System.out.println("Opcao invalida");
        }
    }

    public static void main(String[] args) {
        try {
            Client cliente = new Client();
            cliente.iniciar();
        }catch (IOException err){
            System.out.println("Erro na conexao: " + err);
        }catch(ClassNotFoundException err) {
            System.out.println("Erro no cast: " + err);
        }
    }
}
