package caixaEletronico.client.service.opcaomenu;

import caixaEletronico.client.Cliente;
import caixaEletronico.client.service.OpcaoMenu;
import caixaEletronico.util.Mensagem;
import caixaEletronico.util.Status;

import java.io.IOException;

public class AbrirConta implements OpcaoMenu {
    private Cliente cliente;
    private Mensagem m;

    public AbrirConta() {}

    public AbrirConta(Cliente cliente){
        this.cliente = cliente;
        m = new Mensagem("NOVACONTA");
    }
    @Override
    public void opcaoEscolhida(Cliente c) throws IOException, ClassNotFoundException  {
        AbrirConta abrirConta = new AbrirConta(c);
        abrirConta.abrindoConta();
        c.iniciar();
    }

    private void abrindoConta() throws IOException, ClassNotFoundException {
        System.out.println("Informe nome, senha e saldo inicial, separados por vírgula.");
        System.out.print("EXEMPLO: fulano,detal10,20\n>> ");
        String dados = cliente.newString();

        String[] dadosConta = dados.split(",");
        if(verificacao(dadosConta)){
            cliente.criandoSocket();
            solicitacao(dadosConta);
            respostaServer();
            cliente.finalizandoSocket();
        }else System.out.println("Formato invalido. Tente novamente.");
    }

    private boolean verificacao(String[] dados){
        if(dados.length==3){
            return true;
        } else return false;
    }

    private void solicitacao(String[] dados) throws IOException {
        m.setStatus( Status.SOLICITACAO );
        m.setParam("nome", dados[0]);
        m.setParam("senha", dados[1]);
        m.setParam("saldo", Integer.parseInt(dados[2]));

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

