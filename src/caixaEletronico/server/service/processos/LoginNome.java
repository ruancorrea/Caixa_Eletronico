package caixaEletronico.server.service.processos;

import caixaEletronico.server.Arquivo;
import caixaEletronico.server.Conta;
import caixaEletronico.server.service.Operacao;
import caixaEletronico.util.Mensagem;
import java.io.IOException;
import java.util.HashMap;

public class LoginNome implements Operacao {
    @Override
    public Mensagem execute(Arquivo arquivo, HashMap<String, Conta> contas, Mensagem mensagem)  throws IOException {
        String nome = (String) mensagem.getParam("nome");
        Mensagem reply = new Mensagem("LOGINNOMEREPLY");
        //System.out.println("Operacao: " + reply.getOperacao());
        int achou = 0;

        if(nome == null){
            reply.setStatus( 411 ); // PARAMERROR
        }else{

            for(String p : contas.keySet()){
                if(p.equals(nome)){
                    reply.setStatus( 200 ); // OK
                    reply.setParam("mensagem", "Conta encontrada de nome: " + nome );
                    achou = 1;
                }
            }
            if(achou == 0){
                reply.setStatus( 400 ); // ERROR
                reply.setParam("mensagem", "Conta não encontrada.");
            }
        }

        return reply;
    }
}

