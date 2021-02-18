package com.jpbtrhcs.caixaeletronico.client.service;

import com.jpbtrhcs.caixaeletronico.client.service.opcaomenu.AbrirConta;
import com.jpbtrhcs.caixaeletronico.client.service.opcaomenu.Depositar;
import com.jpbtrhcs.caixaeletronico.client.service.opcaomenu.Sacar;

public enum TipoOpcaoMenu {
    SACAR {
        @Override
        public OpcaoMenu obterOpcaoMenu() {
            return new Sacar();
        }
    },
    DEPOSITAR {
        @Override
        public OpcaoMenu obterOpcaoMenu() {
            return new Depositar();
        }
    },
    ABRIRCONTA {
        @Override
        public OpcaoMenu obterOpcaoMenu() {
            return new AbrirConta();
        }
    };

    public abstract OpcaoMenu obterOpcaoMenu();
}
