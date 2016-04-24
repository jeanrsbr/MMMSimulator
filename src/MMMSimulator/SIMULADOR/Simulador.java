/*
 * MMMSimulator
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package MMMSimulator.SIMULADOR;

import MMMSimulator.CARTEIRA.Carteira;
import MMMSimulator.MERCADO.AtivoException;
import MMMSimulator.MERCADO.Mercado;
import MMMSimulator.PREDICOES.Predicoes;
import MMMSimulator.PREDICOES.TransacaoPredita;
import java.util.Date;
import java.util.SortedSet;

/**
 *
 */
public class Simulador {

    private final Carteira carteira;
    private final Mercado mercado;
    private final Predicoes predicoes;

    public Simulador(Carteira carteira, Mercado mercado, Predicoes predicoes) {
        this.carteira = carteira;
        this.mercado = mercado;
        this.predicoes = predicoes;
    }

    //Simula as transações indicadas no algoritmo
    public void simulaTransacoes() throws AtivoException{

        //Chaves de predições
        SortedSet<Date> chaves = (SortedSet<Date>) predicoes.getPredicoes().keySet();

        //Varre as predições
        for (Date chave : chaves){
            
            //Obtém a transação predita
            TransacaoPredita predita = predicoes.getPredicoes().get(chave);
                     
            //Indica que irá simular a transação
            System.out.println("Predição:" + predita.getAtivo() + " Data:" + predita.getDate() + " StopGain:" + predita.getStopGain() + " StopLoss:" + predita.getStopLoss());
            
            carteira.addDiasPreditos();
            
            double precoAbertura = mercado.getPrecoAbertura(predita.getAtivo(), predita.getDate());
            //Se o StopLoss for menor que o preço de abertura, nem realizará a transação
            if (predita.getStopLoss() <= precoAbertura){
                System.out.println("Transação não foi executada o preço de abertura é menor que o STOPLOSS");
                continue;
            }
            //Calcula o preço do lote de ações
            double precoLote = precoAbertura * 100;
            //Quantidade de Lotes a serem comprados
            double qtdLotesCompras = Math.floor(carteira.getCapitalAtual() / precoLote);
            
            /*
            *   Neste ponto, foram comprados n Lotes de ações pelo preço de abertura do ativo
            */
            
            //Indica que houve movimentação no dia
            carteira.addMovimentacao();
            System.out.println("Foram comprados " + qtdLotesCompras + " de ações ao preço de " + precoAbertura + ".");
            
            //Verifica se houve stop
            double stop = mercado.verificaStop(predita.getAtivo(), chave, predita.getStopLoss(), predita.getStopGain());
            //Se ultrapassou o Stop GAIN ou Stop LOSS
            if (stop != 0d ){
                System.out.println("Ocorreu Stop no meio do dia, ao preço de " + stop + ".");
                carteira.movimentaCarteira(qtdLotesCompras * 100 * precoAbertura, qtdLotesCompras * 100 * stop);
                continue;
            }
            //Obtém o preço de fechamento
            double precoFechamento = mercado.getPrecoFechamento(predita.getAtivo(), predita.getDate());
            System.out.println("Foram vendidas ao final do pregão as ações ao preço de " + precoFechamento + ".");
            carteira.movimentaCarteira(qtdLotesCompras * 100 * precoAbertura, qtdLotesCompras * 100 * precoFechamento);           
            
        }
        
        System.out.println("Simulação encerrada");
        System.out.println("Foram efetuadas " + carteira.getTotalMovimentacoes() + " movimentações em " + carteira.getDiasPreditos() + " dias preditos");
        System.out.println("O capital inicial foi de " + carteira.getCapitalInicial() + " e finalizou em " + carteira.getCapitalAtual());
        
    }
}
