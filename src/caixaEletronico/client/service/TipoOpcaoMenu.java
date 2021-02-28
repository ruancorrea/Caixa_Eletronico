package caixaEletronico.client.service;

import caixaEletronico.client.service.opcaomenu.AbrirConta;
import caixaEletronico.client.service.opcaomenu.Depositar;
import caixaEletronico.client.service.opcaomenu.Sacar;

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

