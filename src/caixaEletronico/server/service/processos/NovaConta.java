package caixaEletronico.server.service.processos;

import caixaEletronico.server.Arquivo;
import caixaEletronico.server.Conta;
import caixaEletronico.server.service.Processo;
import caixaEletronico.util.Mensagem;
import caixaEletronico.util.Status;

import java.io.IOException;
import java.util.HashMap;

public class NovaConta implements Processo {
    @Override
    public Mensagem execute(Arquivo arquivo, HashMap<String,Conta> contas, Mensagem mensagem) throws IOException {
        String nome = (String) mensagem.getParam("nome");
        String senha = (String) mensagem.getParam("senha");
        int saldo = (int) mensagem.getParam("saldo");
        Mensagem reply = new Mensagem("NOVACONTAREPLY");
        int achou = 0;

        if(nome == null || senha == null || saldo < 1){
            reply.setStatus( Status.PARAMERROR );
        }else{

            for(String p : contas.keySet()){
                if(p.equals(nome)){
                    reply.setStatus( Status.ERROR );
                    System.out.println("Conta já existente");
                    achou = 1;
                }
            }
            if(achou == 0){
                reply.setStatus( Status.OK );
                Conta conta = new Conta(nome, senha, saldo);
                contas.put(nome, conta);
                arquivo.salvandoArquivo(contas);
                reply.setStatus( Status.OK );
                reply.setParam("mensagem", "Conta criada com:\nNome: " + nome + "\nSenha: " + senha + "\nSaldo: " + saldo );
            }
        }
        return reply;
    }
}
