package caixaEletronico.server.service;

import caixaEletronico.server.Arquivo;
import caixaEletronico.server.Conta;
import caixaEletronico.server.service.processos.*;
import caixaEletronico.util.Mensagem;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Invoker {
    public Map<String, Operacao> operacoes;

    public Invoker(){
        operacoes = new HashMap<String, Operacao>();
        operacoes.put("DEPOSITO", new Deposito());
        operacoes.put("LOGINNOME", new LoginNome());
        operacoes.put("LOGINSENHA", new LoginSenha());
        operacoes.put("NOVACONTA", new NovaConta());
        operacoes.put("SAQUE", new Saque());
    }

    public Mensagem invoke(String command, Arquivo arquivo, HashMap<String, Conta> contas, Mensagem mensagem) throws IOException {
        return operacoes.get(command).execute(arquivo,contas,mensagem);
    }

}
