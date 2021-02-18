package com.jpbtrhcs.caixaeletronico.server.service;

import com.jpbtrhcs.caixaeletronico.server.Server;
import com.jpbtrhcs.caixaeletronico.util.Mensagem;

import java.io.IOException;

public interface Protocolo {
    public Mensagem execute(Server server) throws IOException;
}
