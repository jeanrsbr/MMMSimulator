/*
 * MMM
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package MMMSimulator.MERCADO;

import MMMSimulator.SimuladorConstants;
import java.io.File;
import java.util.Date;
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

    //Obtém o preço de abertura do ativo
    public double getPrecoAbertura(String ativo, Date date) throws AtivoException{
        return ativos.get(ativo).getPrecoAbertura(date);        
    }    
    
    //Verifica se o ativo alcançou o Stop LOSS ou Stop GAIN durante o dia
    public double verificaStop(String ativo, Date date, double stopLoss, double stopGain){
        return ativos.get(ativo).verificaStop(date, stopLoss, stopGain);
    }
    
    public double getPrecoFechamento(String ativo, Date date) throws AtivoException{
        return ativos.get(ativo).getPrecoFechamento(date);
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
