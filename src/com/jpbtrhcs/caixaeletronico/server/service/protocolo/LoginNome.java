package com.jpbtrhcs.caixaeletronico.server.service.protocolo;

import com.jpbtrhcs.caixaeletronico.server.Server;
import com.jpbtrhcs.caixaeletronico.server.service.Protocolo;
import com.jpbtrhcs.caixaeletronico.util.Mensagem;
import com.jpbtrhcs.caixaeletronico.util.Status;

import java.io.IOException;

public class LoginNome implements Protocolo {
    @Override
    public Mensagem execute(Server server)  throws IOException {
        String nome = (String) server.getM().getParam("nome");
        Mensagem reply = new Mensagem("LOGINNOMEREPLY");
        int achou = 0;

        if(nome == null){
            reply.setStatus( Status.PARAMERROR );
        }else{

            for(String p : server.getContas().keySet()){
                if(p.equals(nome)){
                    reply.setStatus( Status.OK );
                    reply.setParam("mensagem", "Conta encontrada de nome: " + nome );
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
