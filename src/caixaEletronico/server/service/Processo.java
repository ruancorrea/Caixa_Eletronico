package caixaEletronico.server.service;

import caixaEletronico.server.Arquivo;
import caixaEletronico.server.Conta;
import caixaEletronico.util.Mensagem;

import java.io.IOException;
import java.util.HashMap;

public interface Processo {
    public Mensagem execute(Arquivo arquivo, HashMap<String, Conta> contas, Mensagem mensagem) throws IOException;
}
