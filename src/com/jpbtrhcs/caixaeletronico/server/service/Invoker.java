package com.jpbtrhcs.caixaeletronico.server.service;

import com.jpbtrhcs.caixaeletronico.server.Server;
import com.jpbtrhcs.caixaeletronico.server.service.protocolo.*;
import com.jpbtrhcs.caixaeletronico.util.Mensagem;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Invoker {
    public Map<String,Protocolo> protocolos;

    public Invoker(){
        protocolos = new HashMap<String,Protocolo>();
        protocolos.put("DEPOSITO", new Deposito());
        protocolos.put("LOGINNOME", new LoginNome());
        protocolos.put("LOGINSENHA", new LoginSenha());
        protocolos.put("NOVACONTA", new NovaConta());
        protocolos.put("SAQUE", new Saque());
    }

    public Mensagem invoke(Server server, String command) throws IOException {
        return protocolos.get(command).execute(server);
    }

}
