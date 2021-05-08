package caixaEletronico.client.service.login;

import caixaEletronico.client.Cliente;
import caixaEletronico.client.service.Login;
import caixaEletronico.util.Mensagem;
import java.io.IOException;

public class Nome implements Login {
    private Mensagem m;
    public String nome;

    public Nome() {
        m = new Mensagem("LOGINNOME");
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String getSenha() {
        return null;
    }

    @Override
    public int login(Cliente cliente) throws IOException, ClassNotFoundException {
        System.out.println("\n:: Login ::\n");
        System.out.print("Digite o nome da conta. Digite 0 para voltar\n>> ");
        String opcao = cliente.newString();

        if(opcao.equals("0")) {
            cliente.iniciar();
        }

        String[] dadosConta = opcao.split(",");

        if(verificando(dadosConta)){
            setNome(dadosConta[0]);
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
        if(dados.length == 1){
            return true;
        }else return false;
    }

    @Override
    public void solicitacao(Cliente cliente, String[] dados) throws IOException, ClassNotFoundException {
        m.setStatus( 373 ); // SOLICITACAO
        m.setParam("nome", dados[0]);

        cliente.output.writeObject(m);
        cliente.output.flush();
        //System.out.println("\nMensagem " + m + "enviada\n");
    }

    @Override
    public void respostaServer(Cliente cliente) throws IOException, ClassNotFoundException {
        m = (Mensagem) cliente.input.readObject();
        System.out.println("Status " + m.getStatus());
    }
}

