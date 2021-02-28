package caixaEletronico.client.service;

import caixaEletronico.client.Cliente;
import java.io.IOException;

public interface OpcaoMenu {
    public void opcaoEscolhida(Cliente c) throws IOException, ClassNotFoundException ;
}

