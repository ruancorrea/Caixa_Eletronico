package caixaEletronico.client.service;

import caixaEletronico.client.service.login.Nome;
import caixaEletronico.client.service.login.NomeSenha;

public enum TipoLogin {
    NOMESENHA {
        @Override
        public Login obterLogin() {
            return new NomeSenha();
        }
    },
    NOME {
        @Override
        public Login obterLogin() {
            return new Nome();
        }
    };

    public abstract Login obterLogin();
}

