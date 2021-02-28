package caixaEletronico.client.service.opcaomenu;
import caixaEletronico.client.Cliente;
import caixaEletronico.client.service.Login;
import caixaEletronico.client.service.OpcaoMenu;
import caixaEletronico.client.service.TipoLogin;
import caixaEletronico.util.Mensagem;
import caixaEletronico.util.Status;

import java.io.IOException;

public class Depositar implements OpcaoMenu {
    private Cliente cliente;
    private Mensagem m;
    private int valor;
    private String nome;

    public Depositar() {}

    public Depositar(Cliente cliente){
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
    public void opcaoEscolhida(Cliente c) throws IOException, ClassNotFoundException {
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

