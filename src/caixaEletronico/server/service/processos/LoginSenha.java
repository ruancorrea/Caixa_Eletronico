package caixaEletronico.server.service.processos;

import caixaEletronico.server.Arquivo;
import caixaEletronico.server.Conta;
import caixaEletronico.server.service.Processo;
import caixaEletronico.util.Mensagem;
import java.io.IOException;
import java.util.HashMap;

public class LoginSenha implements Processo {
    @Override
    public Mensagem execute(Arquivo arquivo, HashMap<String, Conta> contas, Mensagem mensagem) throws IOException {
        String senha = (String) mensagem.getParam("senha");
        String nome = (String) mensagem.getParam("nome");
        Mensagem reply = new Mensagem("LOGINSENHAREPLY");
        //System.out.println("Operacao: " + reply.getOperacao());
        int achou = 0;

        if(senha == null || nome == null){
            reply.setStatus( 411 ); // PARAMERROR
        }else{

            for(String p : contas.keySet()){
                if(p.equals(nome)){
                    if(contas.get(p).getSenha().equals(senha)){
                        achou = 1;
                        reply.setStatus( 200 ); // OK
                        reply.setParam("mensagem", "Conta encontrada de nome: " + nome + " e senha: " + senha + " tendo saldo de R$ " + contas.get(p).getSaldo());
                    }
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

