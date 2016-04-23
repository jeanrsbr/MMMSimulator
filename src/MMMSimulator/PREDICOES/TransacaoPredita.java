/*
 * MMMSimulator
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package MMMSimulator.PREDICOES;

import java.util.Date;

/**
 * Transações preditas pelo algoritmo SVM
 * O preço de compra será o preço de abertura do mercado
 * O preço de venda será o preço de fechamento do mercado ou o STOP GAIN
 */
public class TransacaoPredita {

    private final int prioridade;
    private final Date date;
    private final String ativo;
    private final double stopGain;
    private final double stopLoss;

    public TransacaoPredita(int prioridade, Date date, String ativo, double stopGain, double stopLoss) {
        this.prioridade = prioridade;
        this.date = date;
        this.ativo = ativo;
        this.stopGain = stopGain;
        this.stopLoss = stopLoss;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public Date getDate() {
        return date;
    }

    public String getAtivo() {
        return ativo;
    }

    public double getStopGain() {
        return stopGain;
    }

    public double getStopLoss() {
        return stopLoss;
    }


}
