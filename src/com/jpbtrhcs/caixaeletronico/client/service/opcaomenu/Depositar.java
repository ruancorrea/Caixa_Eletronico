package com.jpbtrhcs.caixaeletronico.client.service.opcaomenu;

import com.jpbtrhcs.caixaeletronico.client.Client;
import com.jpbtrhcs.caixaeletronico.client.service.Login;
import com.jpbtrhcs.caixaeletronico.client.service.OpcaoMenu;
import com.jpbtrhcs.caixaeletronico.client.service.TipoLogin;
import com.jpbtrhcs.caixaeletronico.util.Mensagem;
import com.jpbtrhcs.caixaeletronico.util.Status;

import java.io.IOException;

public class Depositar implements OpcaoMenu {
    private Client cliente;
    private Mensagem m;
    private int valor;
    private String nome;

    public Depositar() {}

    public Depositar(Client cliente){
        this.cliente = cliente;
        m = new Mensagem("NOVACONTA");
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public void opcaoEscolhida(Client c) throws IOException, ClassNotFoundException {
        Depositar depositar = new Depositar(c);
        TipoLogin tipoLogin = TipoLogin.values()[1];;
        Login login = tipoLogin.obterLogin();
        Status status = login.login(c);
        if (status == Status.OK) {
            depositar.setNome(login.getNome());
            depositar.depositando();
        }else System.out.println("Erro no login: " + status);

        c.iniciar();
    }

    private boolean verificando(int valor){
        if(valor>500){
            System.out.println("Válor máximo ultrapassado.");
            return false;
        }
        if(valor<1){
            System.out.println("Valor digitado inválido.");
            return false;
        }
        return true;
    }

    private void depositando() throws IOException, ClassNotFoundException {
        System.out.print("Digite o valor que quer depositar. Deposito máximo de 500 reais.(apenas numeros, ex: 200)\n>> ");
        valor = cliente.newInt();
        if(verificando(valor)){
            cliente.criandoSocket();
            solicitacao();
            respostaServer();
            cliente.finalizandoSocket();
        }
    }

    private void solicitacao() throws IOException, ClassNotFoundException{
        m = new Mensagem("DEPOSITO");
        m.setStatus( Status.SOLICITACAO );
        m.setParam("nome", getNome());
        m.setParam("valor", valor);

        cliente.output.writeObject(m);
        cliente.output.flush();
        System.out.println("Mensagem " + m + " enviada");
    }

    private void respostaServer() throws IOException, ClassNotFoundException {
        m = (Mensagem) cliente.input.readObject();
        System.out.println("Resposta " + m);

        if(m.getStatus() == Status.OK){
            String resposta = (String) m.getParam("mensagem");
            System.out.println("Mensagem: " + resposta);
        }else{
            System.out.println("Erro: " + m.getStatus());
        }
    }
}
