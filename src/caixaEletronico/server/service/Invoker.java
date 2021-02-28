package caixaEletronico.server.service;

import caixaEletronico.server.Arquivo;
import caixaEletronico.server.Conta;
import caixaEletronico.server.service.processos.*;
import caixaEletronico.util.Mensagem;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Invoker {
    public Map<String,Processo> processos;

    public Invoker(){
        processos = new HashMap<String,Processo>();
        processos.put("DEPOSITO", new Deposito());
        processos.put("LOGINNOME", new LoginNome());
        processos.put("LOGINSENHA", new LoginSenha());
        processos.put("NOVACONTA", new NovaConta());
        processos.put("SAQUE", new Saque());
    }

    public Mensagem invoke(String command, Arquivo arquivo, HashMap<String, Conta> contas, Mensagem mensagem) throws IOException {
        return processos.get(command).execute(arquivo,contas,mensagem);
    }

}
