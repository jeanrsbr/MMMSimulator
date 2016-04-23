/*
 * MMM
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package MMMSimulator.MERCADO;

import MMMSimulator.PREDICOES.TransacaoPredita;
import MMMSimulator.SimuladorConstants;
import java.io.File;
import java.util.HashMap;

/**
 * Descrição da classe.
 */
public class Mercado {

    private HashMap<String, Ativo> ativos;

    public Mercado() throws AtivoException, MercadoException {

        //Instância o objeto que contém a lista de ativos
        ativos = new HashMap<>();
        populaMercado();

    }



    //Cria mercado a partir dos dados do simulador
    private void populaMercado() throws AtivoException, MercadoException {

        File simulador = new File(SimuladorConstants.SIMULADOR_FOLDER);
        //Obtém a lista de arquivos do diretório
        String[] listaArquivos = simulador.list();
        //Se não possui arquivos no diretório
        if (listaArquivos.length == 0){
            throw new MercadoException("O diretório para simulação está vazio");
        }

        //Varre os arquivos do diretório para instanciar os objetos ativos
        for (String listaArquivo : listaArquivos) {
            //Nome do ativo
            String ativo = listaArquivo.split(SimuladorConstants.SIMULADOR_EXT)[0];
            //Instância o objeto ativo
            ativos.put(ativo, new Ativo(listaArquivo, ativo));
        }
    }
}
