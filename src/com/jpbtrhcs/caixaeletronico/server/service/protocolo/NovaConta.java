package com.jpbtrhcs.caixaeletronico.server.service.protocolo;

import com.jpbtrhcs.caixaeletronico.server.Server;
import com.jpbtrhcs.caixaeletronico.server.Conta;
import com.jpbtrhcs.caixaeletronico.server.service.Protocolo;
import com.jpbtrhcs.caixaeletronico.util.Mensagem;
import com.jpbtrhcs.caixaeletronico.util.Status;

import java.io.IOException;

public class NovaConta implements Protocolo {
    @Override
    public Mensagem execute(Server server) throws IOException {
        String nome = (String) server.getM().getParam("nome");
        String senha = (String) server.getM().getParam("senha");
        int saldo = (int) server.getM().getParam("saldo");
        Mensagem reply = new Mensagem("NOVACONTAREPLY");
        int achou = 0;

        if(nome == null || senha == null || saldo < 1){
            reply.setStatus( Status.PARAMERROR );
        }else{

            for(String p : server.getContas().keySet()){
                if(p.equals(nome)){
                    reply.setStatus( Status.ERROR );
                    System.out.println("Conta já existente");
                    achou = 1;
                }
            }
            if(achou == 0){
                reply.setStatus( Status.OK );
                Conta conta = new Conta(nome, senha, saldo);
                server.getContas().put(nome, conta);
                server.getArquivo().salvandoArquivo(server.getContas());
                reply.setStatus( Status.OK );
                reply.setParam("mensagem", "Conta criada com:\nNome: " + nome + "\nSenha: " + senha + "\nSaldo: " + saldo );
            }
        }
        return reply;
    }
}
