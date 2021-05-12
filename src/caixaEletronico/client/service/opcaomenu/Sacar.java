package caixaEletronico.client.service.opcaomenu;

import caixaEletronico.client.Cliente;
import caixaEletronico.client.service.Login;
import caixaEletronico.client.service.OpcaoMenu;
import caixaEletronico.client.service.TipoLogin;
import caixaEletronico.util.Mensagem;
import java.io.IOException;

public class Sacar implements OpcaoMenu {
    private Cliente cliente;
    private Mensagem m;
    private int valor;
    private String nome;
    private String senha;

    public Sacar() { }

    public Sacar(Cliente cliente){
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
    public void opcaoEscolhida(Cliente c) throws IOException, ClassNotFoundException {
        Sacar sacar = new Sacar(c);
        TipoLogin tipoLogin = TipoLogin.values()[0];
        Login login = tipoLogin.obterLogin();
        int status = login.login(c);
        if (status == 200) { // OK
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
        m.setStatus( 373 ); // SOLICITACAO
        m.setParam("nome", getNome());
        m.setParam("senha", getSenha());
        m.setParam("valor", getValor());

        cliente.output.writeObject(m);
        cliente.output.flush();
      //  System.out.println("\nMensagem " + m + "enviada\n");
    }

    private void respostaServer() throws IOException, ClassNotFoundException {
        m = (Mensagem) cliente.input.readObject();
        System.out.println("Status: " + m.getStatus());
        System.out.println("Resposta: " + m.getParam("mensagem") + "\n");

    }
}

