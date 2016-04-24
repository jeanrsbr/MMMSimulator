/*
 * MMMSimulator
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package MMMSimulator.CARTEIRA;

import MMMSimulator.MISC.LeituraProperties;

/**
 * Descrição da classe.
 */
public class Carteira {

    private int diasPreditos;
    private int totalMovimentacoes;
    private final double capitalInicial;
    private double capitalAtual;
    private double totalDeIRRF;
    private double totalDeCorretagem;
    private final double valorCorretagem;

    public Carteira() throws CarteiraException {
        capitalInicial = LeituraProperties.getInstance().leituraPropertiesDouble("prop.capital");
        valorCorretagem = LeituraProperties.getInstance().leituraPropertiesDouble("prop.valorCorretagem");
        //Se não foi informado capital inicial
        if (capitalInicial == 0){
            throw new CarteiraException("Não foi informado capital inicial para a simulação");
        }

    }
    
    //Movimenta os valores da carteira
    public void movimentaCarteira(double valorComprado, double valorVendido){
        capitalAtual = capitalAtual - valorComprado + valorVendido;
        double resultado =  valorVendido - valorComprado;
        //Se houve lucro
        if (resultado > 0){
            double iRRF = resultado * 0.20;
            capitalAtual = capitalAtual - iRRF;
            totalDeIRRF = totalDeIRRF + iRRF;
        }
        totalDeCorretagem = totalDeCorretagem + valorCorretagem;
        capitalAtual = capitalAtual - valorCorretagem;        
    }

    //Adiciona movimentação
    public void addMovimentacao(){
        totalMovimentacoes++;
    }

    //Adiciona dias negociados
    public void addDiasPreditos(){
        diasPreditos++;
    }

    public int getTotalMovimentacoes() {
        return totalMovimentacoes;
    }

    public double getCapitalAtual() {
        return capitalAtual;
    }

    public double getCapitalInicial() {
        return capitalInicial;
    }

    public int getDiasPreditos() {
        return diasPreditos;
    }

}
