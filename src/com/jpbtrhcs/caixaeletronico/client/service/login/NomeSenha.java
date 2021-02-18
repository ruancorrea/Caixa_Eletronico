package com.jpbtrhcs.caixaeletronico.client.service.login;

import com.jpbtrhcs.caixaeletronico.client.Client;
import com.jpbtrhcs.caixaeletronico.client.service.Login;
import com.jpbtrhcs.caixaeletronico.util.Mensagem;
import com.jpbtrhcs.caixaeletronico.util.Status;

import java.io.IOException;

public class NomeSenha implements Login {
    private Mensagem m;
    public String nome;
    public String senha;

    public NomeSenha() {
        m = new Mensagem("LOGINSENHA");
    }

    @Override
    public Status login(Client cliente) throws IOException, ClassNotFoundException {
        System.out.println("\n:: Login ::\n");
        System.out.print("Digite o nome e a senha da conta, separadas por um virgula. EXEMPLO fulano,detal10\nDigite 0 para voltar\n>> ");

        String opcao = cliente.newString();

        if(opcao.equals("0")) {
            cliente.iniciar();
        }

        String[] dadosConta = opcao.split(",");

        if(verificando(dadosConta)){
            setNome(dadosConta[0]);
            setSenha(dadosConta[1]);

            cliente.criandoSocket();
            solicitacao(cliente, dadosConta);
            respostaServer(cliente);
            cliente.finalizandoSocket();
        }else{
            System.out.println("É necessário inserir as informações corretas.");
        }

        return m.getStatus();
    }

    @Override
    public boolean verificando(String[] dados) {
        if(dados.length == 2){
            return true;
        }else return false;
    }

    @Override
    public void solicitacao(Client cliente, String[] dados) throws IOException, ClassNotFoundException {
        m.setStatus( Status.SOLICITACAO );
        m.setParam("nome", dados[0]);
        m.setParam("senha", dados[1]);

        cliente.output.writeObject(m);
        cliente.output.flush();
        System.out.println("Mensagem " + m + " enviada");
    }

    @Override
    public void respostaServer(Client cliente) throws IOException, ClassNotFoundException {
        m = (Mensagem) cliente.input.readObject();
        System.out.println("Resposta " + m);



        if(m.getStatus() == Status.OK){
            String resposta = (String) m.getParam("mensagem");
            System.out.println("Mensagem: " + resposta);
        }else{
            System.out.println("Erro: " + m.getStatus());
        }
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getSenha() {
        return senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
