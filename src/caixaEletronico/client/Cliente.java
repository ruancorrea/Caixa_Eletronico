package caixaEletronico.client;

import caixaEletronico.client.service.OpcaoMenu;
import caixaEletronico.client.service.TipoOpcaoMenu;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Cliente {
    private Scanner entrada;
    public String nome;

    public Socket socket;
    public ObjectOutputStream output;
    public ObjectInputStream input;

    public Cliente(){
        entrada = new Scanner(System.in);
    }

    public String getNome()
    {
        return this.nome;
    }

    public String newString(){
        return entrada.nextLine();
    }

    public int newInt() {
        int valor = 0;
        while(true){
            try{
                valor = entrada.nextInt();
                break;
            }catch(InputMismatchException e){
                System.out.print("\nO valor inserido nao eh inteiro. Tente novamente.\n>> ");
                entrada.next();
            }
        }
        return valor;
    }

    public void criandoSocket() throws IOException {
        socket = new Socket("localhost", 5000);
        System.out.println("\nConectado com o servidor.\n");
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    public void finalizandoSocket() throws IOException {
        input.close();
        output.close();
        socket.close();
        System.out.println("Conexao com o servidor finalizada.\n");
    }

    public void iniciar() throws IOException, ClassNotFoundException {
        System.out.print("(1) sacar , (2) depositar, (3) abrir conta, (4) finalizar\n>> ");
        int opcao = newInt();
        if(opcao > 0 && opcao < 4){
            TipoOpcaoMenu tipoOpcaoMenu = TipoOpcaoMenu.values()[opcao - 1];
            OpcaoMenu opcaoMenu = tipoOpcaoMenu.obterOpcaoMenu();
            opcaoMenu.opcaoEscolhida(new Cliente());
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
            Cliente cliente = new Cliente();
            cliente.iniciar();
        }catch (IOException err){
            System.out.println("Erro na conexao: " + err);
        }catch(ClassNotFoundException err) {
            System.out.println("Erro no cast: " + err);
        }
    }
}

