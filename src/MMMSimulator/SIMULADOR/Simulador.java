/*
 * MMMSimulator
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package MMMSimulator.SIMULADOR;

import MMMSimulator.CARTEIRA.Carteira;
import MMMSimulator.MERCADO.Mercado;
import MMMSimulator.PREDICOES.Predicoes;
import java.util.Date;
import java.util.SortedSet;

/**
 *
 */
public class Simulador {

    private Carteira carteira;
    private Mercado mercado;
    private Predicoes predicoes;

    public Simulador(Carteira carteira, Mercado mercado, Predicoes predicoes) {
        this.carteira = carteira;
        this.mercado = mercado;
        this.predicoes = predicoes;
    }

    //Simula as transações indicadas no algoritmo
    public void simulaTransacoes(){

        //Chaves de predições
        SortedSet<Date> chaves = (SortedSet<Date>) predicoes.getPredicoes().keySet();

        //Varre as predições
        for (Date chave : chaves){
            
        }
    }
}
