package com.jpbtrhcs.caixaeletronico.server.service.protocolo;

import com.jpbtrhcs.caixaeletronico.server.Server;
import com.jpbtrhcs.caixaeletronico.server.service.Protocolo;
import com.jpbtrhcs.caixaeletronico.util.Mensagem;
import com.jpbtrhcs.caixaeletronico.util.Status;

import java.io.IOException;

public class LoginSenha implements Protocolo {
    @Override
    public Mensagem execute(Server server) throws IOException {
        String senha = (String) server.getM().getParam("senha");
        String nome = (String) server.getM().getParam("nome");
        Mensagem reply = new Mensagem("LOGINSENHAREPLY");
        int achou = 0;

        if(senha == null || nome == null){
            reply.setStatus( Status.PARAMERROR );
        }else{

            for(String p : server.getContas().keySet()){
                if(p.equals(nome)){
                    if(server.getContas().get(p).getSenha().equals(senha)){
                        achou = 1;
                        reply.setStatus( Status.OK );
                        reply.setParam("mensagem", "Conta encontrada de nome: " + nome + " e senha: " + senha + " tendo saldo de R$ " + server.getContas().get(p).getSaldo());
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
