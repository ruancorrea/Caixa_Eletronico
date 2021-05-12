package caixaEletronico.server;

import java.io.*;
import java.util.HashMap;

public class Arquivo {
    
    private File arquivo;
    private BufferedReader leitor;
    private BufferedWriter gravador;
    public Arquivo(String path){
        this.arquivo = new File(path);
    }

    public void lerArquivo(HashMap<String, Conta> contas) throws IOException {
        System.out.println("Carregando dados do arquivo...");
        leitor = new BufferedReader(new FileReader(arquivo));
        String dados = "";

        while ((dados = leitor.readLine()) != null) {
            String[] dadosConta = dados.split(",");
            Conta conta = new Conta(dadosConta[0], dadosConta[1], Integer.parseInt(dadosConta[2]));
            //System.out.println(conta.getNome());
            contas.put(conta.getNome(), conta);
        }
        System.out.println("Dados carregados.");
        leitor.close();
        leitor = null;
    }

    public void salvandoArquivo(HashMap<String, Conta> contas) throws IOException {
        if (contas != null) {
            gravador = new BufferedWriter(new FileWriter(arquivo, false));
            for (Conta conta : contas.values()) {
                String representacaoConta = conta.getNome() + "," + conta.getSenha() + "," + conta.getSaldo();
                gravador.write(representacaoConta + System.getProperty("line.separator"));
            }
            gravador.flush();
            gravador.close();
            gravador = null;
        }
    }
}

