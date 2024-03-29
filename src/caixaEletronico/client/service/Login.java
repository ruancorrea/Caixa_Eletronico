package caixaEletronico.client.service;

import caixaEletronico.client.Cliente;
import java.io.IOException;

public interface Login {

    public int login(Cliente client) throws IOException, ClassNotFoundException;

    public boolean verificando(String[] dados);

    public void solicitacao(Cliente cliente, String[] dados) throws IOException, ClassNotFoundException;

    public void respostaServer(Cliente cliente) throws IOException, ClassNotFoundException;

    public String getNome();

    public String getSenha();

}

