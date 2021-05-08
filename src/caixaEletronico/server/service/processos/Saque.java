package caixaEletronico.server.service.processos;

import caixaEletronico.server.Arquivo;
import caixaEletronico.server.Conta;
import caixaEletronico.server.service.Processo;
import caixaEletronico.util.Mensagem;
import java.io.IOException;
import java.util.HashMap;

public class Saque implements Processo {
    @Override
    public Mensagem execute(Arquivo arquivo, HashMap<String, Conta> contas, Mensagem mensagem)  throws IOException {
        String nome = (String) mensagem.getParam("nome");
        String senha = (String) mensagem.getParam("senha");
        int valor = (int) mensagem.getParam("valor");
        Mensagem reply = new Mensagem("SAQUEREPLY");
        //System.out.println("Operacao: " + reply.getOperacao());
        int achou = 0;

        if(nome == null || senha == null || valor < 1){
            reply.setStatus( 411 ); // PARAMERROR
        }else{

            for(String p : contas.keySet()){
                if(p.equals(nome)){
                    if(contas.get(p).getSenha().equals(senha)){
                        if(valor > contas.get(p).getSaldo()){
                            reply.setStatus( 400 ); // ERROR
                            reply.setParam("mensagem", "Saldo insuficiente para a operação");
                            return reply;
                        }
                        reply = sacar(contas, contas.get(p), valor, arquivo, reply);
                        achou = 1;
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

    public synchronized Mensagem sacar(HashMap<String, Conta> contas, Conta conta, int valor, Arquivo arquivo, Mensagem reply) throws IOException {
        if (conta.getSaldo() >= valor) {
            int saldoOriginal = conta.getSaldo();
            try {
                System.out.println("[" + conta.getNome() + "] Processando saque, aguarde...");
                Thread.sleep(500); // tempo de processamento
            } catch (InterruptedException exception) {
                System.out.println("Error: " + exception);
            }
            conta.setSaldo(conta.getSaldo() - valor);
            System.out.println("[" + conta.getNome() + "] saque de " + valor + ". [Saldo original=" + saldoOriginal + ", Saldo final=" + conta.getSaldo() + "]");
            contas.put(conta.getNome(), conta);
            arquivo.salvandoArquivo(contas);
            reply.setStatus( 200 ); // OK
            reply.setParam("mensagem", "Saque feito no valor de R$ " + valor + " na conta encontrada de nome: " + conta.getNome());

        } else {
            System.out.println("[" + conta.getNome() + "] Nao eh possivel sacar!");
            reply.setStatus( 400 ); // ERROR
            reply.setParam("mensagem", "Nao eh possivel sacar!");
        }
        return reply;
    }
}

