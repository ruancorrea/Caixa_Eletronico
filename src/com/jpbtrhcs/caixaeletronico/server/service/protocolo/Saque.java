package com.jpbtrhcs.caixaeletronico.server.service.protocolo;

import com.jpbtrhcs.caixaeletronico.server.Server;
import com.jpbtrhcs.caixaeletronico.server.service.Protocolo;
import com.jpbtrhcs.caixaeletronico.util.Mensagem;
import com.jpbtrhcs.caixaeletronico.util.Status;

import java.io.IOException;

public class Saque implements Protocolo {
    @Override
    public Mensagem execute(Server server)  throws IOException {
        String nome = (String) server.getM().getParam("nome");
        String senha = (String) server.getM().getParam("senha");
        int valor = (int) server.getM().getParam("valor");
        Mensagem reply = new Mensagem("SAQUEREPLY");
        int achou = 0;

        if(nome == null || senha == null || valor < 1){
            reply.setStatus( Status.PARAMERROR );
        }else{

            for(String p : server.getContas().keySet()){
                if(p.equals(nome)){
                    if(server.getContas().get(p).getSenha().equals(senha)){
                        Server serverCOPY = new Server(server.getContas().get(nome), valor, server.getContas(), server.getArquivo(), server.getGravador());
                        new Thread(serverCOPY, nome).start();

                        achou = 1;
                        reply.setStatus( Status.OK );
                        reply.setParam("mensagem", "Saque feito no valor de R$ " + valor + " na conta encontrada de nome: " + nome);
                    }
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
