package com.jpbtrhcs.caixaeletronico.client.service;

import com.jpbtrhcs.caixaeletronico.client.Client;
import com.jpbtrhcs.caixaeletronico.util.Status;

import java.io.IOException;

public interface Login {

    public Status login(Client client) throws IOException, ClassNotFoundException;

    public boolean verificando(String[] dados);

    public void solicitacao(Client cliente, String[] dados) throws IOException, ClassNotFoundException;

    public void respostaServer(Client cliente) throws IOException, ClassNotFoundException;

    public String getNome();

    public String getSenha();

}
