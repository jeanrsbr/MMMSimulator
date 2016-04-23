/*
 * MMMSimulator
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package MMMSimulator;

import MMMSimulator.CARTEIRA.Carteira;
import MMMSimulator.CARTEIRA.CarteiraException;
import MMMSimulator.MERCADO.AtivoException;
import MMMSimulator.MERCADO.Mercado;
import MMMSimulator.MERCADO.MercadoException;
import MMMSimulator.PREDICOES.Predicoes;
import java.io.File;

/**
 * Descrição da classe.
 */
public class Main {

    public static void main(String[] args) {

        //Instância o arquivo de propriedades
        File properties = new File("properties/dados.properties");
        //Verifica se existe o arquivo de propriedades
        if (!properties.exists()) {
            System.out.
                    println("Não existe o arquivo de propriedades no diretório \n" + properties.getAbsolutePath());
            return;
        }

        try {


            Mercado mercado = new Mercado();
            Carteira carteira = new Carteira();
            Predicoes predicoes = new Predicoes();

            

        } catch (MercadoException | AtivoException | CarteiraException ex) {
            ex.getMessage();
            ex.printStackTrace();
        }

    }

}
