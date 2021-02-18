package com.jpbtrhcs.caixaeletronico.client.service;

import com.jpbtrhcs.caixaeletronico.client.service.login.Nome;
import com.jpbtrhcs.caixaeletronico.client.service.login.NomeSenha;

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
