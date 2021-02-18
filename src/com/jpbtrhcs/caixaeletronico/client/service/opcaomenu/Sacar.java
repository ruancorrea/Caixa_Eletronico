package com.jpbtrhcs.caixaeletronico.client.service.opcaomenu;

import com.jpbtrhcs.caixaeletronico.client.Client;
import com.jpbtrhcs.caixaeletronico.client.service.Login;
import com.jpbtrhcs.caixaeletronico.client.service.OpcaoMenu;
import com.jpbtrhcs.caixaeletronico.client.service.TipoLogin;
import com.jpbtrhcs.caixaeletronico.util.Mensagem;
import com.jpbtrhcs.caixaeletronico.util.Status;

import java.io.IOException;

public class Sacar implements OpcaoMenu {
    private Client cliente;
    private Mensagem m;
    private int valor;
    private String nome;
    private String senha;

    public Sacar() { }

    public Sacar(Client cliente){
        this.cliente = cliente;
        m = new Mensagem("SAQUE");
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public int getValor() {
        return valor;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public void opcaoEscolhida(Client c) throws IOException, ClassNotFoundException {
        Sacar sacar = new Sacar(c);
        TipoLogin tipoLogin = TipoLogin.values()[0];
        Login login = tipoLogin.obterLogin();
        Status status = login.login(c);
        if (status == Status.OK) {
            sacar.setNome(login.getNome());
            sacar.setSenha(login.getSenha());
            sacar.sacando();
        }else System.out.println("Erro no login: " + status);

        c.iniciar();
    }

    private boolean verificando(int valor){
        if(valor<1){
            System.out.println("Valor digitado inválido.");
            return false;
        }
        return true;
    }

    private void sacando() throws IOException, ClassNotFoundException {
        System.out.print("Digite o valor que você quer sacar. Apenas numeros inteiros. EXEMPLO: 200\n>> ");
        valor = cliente.newInt();
        if(verificando(valor)){
            cliente.criandoSocket();
            solicitacao();
            respostaServer();
            cliente.finalizandoSocket();
        }
    }

    private void solicitacao() throws IOException, ClassNotFoundException {
        m.setStatus( Status.SOLICITACAO );
        m.setParam("nome", getNome());
        m.setParam("senha", getSenha());
        m.setParam("valor", getValor());

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
