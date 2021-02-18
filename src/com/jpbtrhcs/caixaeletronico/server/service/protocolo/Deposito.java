package com.jpbtrhcs.caixaeletronico.server.service.protocolo;

import com.jpbtrhcs.caixaeletronico.server.Server;
import com.jpbtrhcs.caixaeletronico.server.Conta;
import com.jpbtrhcs.caixaeletronico.server.service.Protocolo;
import com.jpbtrhcs.caixaeletronico.util.Mensagem;
import com.jpbtrhcs.caixaeletronico.util.Status;

import java.io.IOException;

public class Deposito implements Protocolo {
    @Override
    public Mensagem execute(Server server)  throws IOException {
        String nome = (String) server.getM().getParam("nome");
        int valor = (int) server.getM().getParam("valor");
        Mensagem reply = new Mensagem("DEPOSITOREPLY");
        int achou = 0;

        if(nome == null || valor < 1){
            reply.setStatus( Status.PARAMERROR );
        }else{

            for(String p : server.getContas().keySet()){
                if(p.equals(nome)){
                    Conta conta = new Conta(server.getContas().get(p).getNome(), server.getContas().get(p).getSenha(), server.getContas().get(p).getSaldo()+valor);
                    server.getContas().put(nome, conta);
                    server.getArquivo().salvandoArquivo(server.getContas());
                    reply.setStatus( Status.OK );
                    reply.setParam("mensagem", "Deposito de " + valor + " feito da conta de nome: " + nome );
                    achou = 1;
                }
            }
            if(achou == 0){
                reply.setStatus( Status.ERROR );
                reply.setParam("mensagem", "Conta não encontrada.");
            }
        }
        return reply;
    }
}
