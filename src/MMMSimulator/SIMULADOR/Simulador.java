/*
 * MMMSimulator
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package MMMSimulator.SIMULADOR;

import MMMSimulator.CARTEIRA.Carteira;
import MMMSimulator.MERCADO.AtivoException;
import MMMSimulator.MERCADO.Mercado;
import MMMSimulator.MERCADO.MercadoException;
import MMMSimulator.PREDICOES.Predicoes;
import MMMSimulator.PREDICOES.TransacaoPredita;
import java.text.SimpleDateFormat;
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
    public void simulaTransacoes() throws MercadoException, AtivoException {

        //Chaves de predições
        SortedSet<Date> chaves = (SortedSet<Date>) predicoes.getPredicoes().keySet();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        Date chaveOld = null;
        //Varre as predições
        for (Date chave : chaves) {

            //Se possui chave antiga e o mês é diferente do atual
            if (chaveOld != null && chaveOld.getMonth() != chave.getMonth()){
                //Realiza o fechamento da carteira do mês anterior
                carteira.apuraMes();
            }
            
            //Atualiza a chave antiga
            chaveOld = chave;
            
            //Obtém a transação predita
            TransacaoPredita predita = predicoes.getPredicoes().get(chave);

            //Indica que irá simular a transação
            System.out.println("Predição:" + predita.getAtivo() + " Data:" + formatter.format(predita.getDate()) + " StopGain:" + predita.getStopGain());

            carteira.addDiasPreditos();
            
            double precoAbertura;
            
            try {
                precoAbertura = mercado.getPrecoAbertura(predita.getAtivo(), predita.getDate());
            } catch (AtivoException ex) {
                ex.getMessage();
                continue;
            }

            
            System.out.println("Preço de abertura:" + precoAbertura);

            if (precoAbertura > predita.getStopGain()) {
                System.out.println("Transação não foi executada o preço de abertura(" + precoAbertura + ") é maior que o STOPGAIN");
                continue;
            }

            //Calcula o valor de Stop
            //double stopLoss = precoAbertura - ((predita.getStopGain() - precoAbertura) * 0.33);
            double stopLoss = 0d;
                    
                    
            //Calcula o preço do lote de ações
            double precoLote = precoAbertura * 100;
            //Quantidade de Lotes a serem comprados
            double qtdLotesCompras = Math.floor(carteira.getCapitalAtual() / precoLote);

            /*
            *   Neste ponto, foram comprados n Lotes de ações pelo preço de abertura do ativo
             */
            //Indica que houve movimentação no dia
            carteira.addMovimentacao();
            System.out.println("Foram comprados " + qtdLotesCompras + " lotes de ações ao preço de " + precoAbertura + " cada.");

            //Verifica se houve stop
            double stop = mercado.verificaStop(predita.getAtivo(), chave, stopLoss, predita.getStopGain());
            //Se ultrapassou o Stop GAIN ou Stop LOSS
            if (stop != 0d) {
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
        System.out.println("Foram pagos " + carteira.getTotalDeCorretagem() + " de corretagem");
        System.out.println("Foram pagos " + carteira.getTotalDeIRRF() + " de IRRF");
        System.out.println("Operações com lucro " + carteira.getOperaçõesComLucro());
        System.out.println("Operações com prejuízo " + carteira.getOperaçõesComPrejuízo());

    }
}
