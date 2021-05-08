package caixaEletronico.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Mensagem implements Serializable{
    private int status;
    private String operacao; // LOGINsenha, LOGINnome, SAQUE, DEPOSITO, ABRIRconta

    Map<String, Object> params;

    public Mensagem(String operacao)
    {
        this.operacao = operacao;
        params = new HashMap<>();
    }

    public String getOperacao(){
        return operacao;
    }

    public int getStatus(){
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setParam(String chave, Object valor) {
        params.put(chave, valor);
    }

    public Object getParam(String chave) {
        return params.get(chave);
    }

    @Override
    public String toString(){
        String m = "\nOperacao: " + operacao;
        m += "\nStatus: " + status;
        m += "\nParametros:";
        for(String p : params.keySet()){
            m += "\n" + p + ": " + params.get(p);
        }
        m += "\n";
        return m;
    }
}
