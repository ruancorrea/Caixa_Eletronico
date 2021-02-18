package com.jpbtrhcs.caixaeletronico.server;

public class Conta {
    public String nome;
    private String senha;
    private int saldo;
    public Conta() {}
    public Conta(String nome, String senha, int saldo) {
        this.nome = nome;
        this.senha = senha;
        this.saldo = saldo;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha()
    {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getSaldo()
    {
        return saldo;
    }
}

