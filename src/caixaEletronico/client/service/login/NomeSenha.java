package caixaEletronico.client.service.login;

import caixaEletronico.client.Cliente;
import caixaEletronico.client.service.Login;
import caixaEletronico.util.Mensagem;
import java.io.IOException;

public class NomeSenha implements Login {
    private Mensagem m;
    public String nome;
    public String senha;

    public NomeSenha() {
        m = new Mensagem("LOGINSENHA");
    }

    @Override
    public int login(Cliente cliente) throws IOException, ClassNotFoundException {
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
    public void solicitacao(Cliente cliente, String[] dados) throws IOException, ClassNotFoundException {
        m.setStatus( 373 ); // SOLICITACAO
        m.setParam("nome", dados[0]);
        m.setParam("senha", dados[1]);

        cliente.output.writeObject(m);
        cliente.output.flush();
       // System.out.println("\nMensagem " + m + "enviada\n");
    }

    @Override
    public void respostaServer(Cliente cliente) throws IOException, ClassNotFoundException {
        m = (Mensagem) cliente.input.readObject();
        System.out.println("Status " + m.getStatus());
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

