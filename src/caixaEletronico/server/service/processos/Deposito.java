package caixaEletronico.server.service.processos;

import caixaEletronico.server.Arquivo;
import caixaEletronico.server.Conta;
import caixaEletronico.server.service.Processo;
import caixaEletronico.util.Mensagem;
import caixaEletronico.util.Status;

import java.io.IOException;
import java.util.HashMap;

public class Deposito implements Processo {
    @Override
    public Mensagem execute(Arquivo arquivo, HashMap<String,Conta> contas, Mensagem mensagem)  throws IOException {
        String nome = (String) mensagem.getParam("nome");
        int valor = (int) mensagem.getParam("valor");
        Mensagem reply = new Mensagem("DEPOSITOREPLY");
        int achou = 0;

        if(nome == null || valor < 1){
            reply.setStatus( Status.PARAMERROR );
        }else{

            for(String p : contas.keySet()){
                if(p.equals(nome)){
                    Conta conta = new Conta(contas.get(p).getNome(), contas.get(p).getSenha(), contas.get(p).getSaldo()+valor);
                    contas.put(nome, conta);
                    arquivo.salvandoArquivo(contas);
                    reply.setStatus( Status.OK );
                    reply.setParam("mensagem", "Deposito de " + valor + " feito da conta de nome: " + nome );
                    return reply;
                }
            }
            reply.setStatus( Status.ERROR );
            reply.setParam("mensagem", "Conta não encontrada.");
        }
        return reply;
    }
}

