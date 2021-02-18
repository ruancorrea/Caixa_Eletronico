package com.jpbtrhcs.caixaeletronico.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Mensagem implements Serializable{
    private Status status;
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

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status) {
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
        String m = "Operacao: " + operacao;
        m += "\nStatus: " + status;
        m += "\nParametros:\n ";
        for(String p : params.keySet()){
            m += "\n" + p + ": " + params.get(p);
        }
        return m;
    }
}

