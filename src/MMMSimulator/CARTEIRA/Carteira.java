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

    private int diasNegociados;
    private int totalMovimentacoes;
    private final double capitalInicial;
    private double capitalFinal;
    private double totalDeIRRF;
    private double totalDeCorretagem;

    public Carteira() throws CarteiraException {
        capitalInicial = LeituraProperties.getInstance().leituraPropertiesDouble("prop.capital");
        //Se não foi informado capital inicial
        if (capitalInicial == 0){
            throw new CarteiraException("Não foi informado capital inicial para a simulação");
        }

    }

    //Adiciona movimentação
    public void addMovimentacao(){
        totalMovimentacoes++;
    }

    //Adiciona dias negociados
    public void addDiasNegociados(){
        diasNegociados++;
    }

    public int getTotalMovimentacoes() {
        return totalMovimentacoes;
    }

    public double getCapitalFinal() {
        return capitalFinal;
    }

    public void setCapitalFinal(double capitalFinal) {
        this.capitalFinal = capitalFinal;
    }

    public double getCapitalInicial() {
        return capitalInicial;
    }

    public int getDiasNegociados() {
        return diasNegociados;
    }

}
