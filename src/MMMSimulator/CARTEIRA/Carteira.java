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
    private double capitalInicialMes;
    private double capitalAtual;
    private double totalDeIRRF;
    private double totalDeIRRFMes;
    private double totalDeCorretagem;
    private int operaçõesComLucro;
    private int operaçõesComPrejuízo;
    private final double valorCorretagem;

    public Carteira() throws CarteiraException {
        capitalInicial = capitalAtual = LeituraProperties.getInstance().leituraPropertiesDouble("prop.capital");
        valorCorretagem = LeituraProperties.getInstance().leituraPropertiesDouble("prop.valorCorretagem");
        //Se não foi informado capital inicial
        if (capitalInicial == 0){
            throw new CarteiraException("Não foi informado capital inicial para a simulação");
        }
        capitalInicialMes = capitalInicial;
    }
    
    //Fecha a carteira para o mês
    public void apuraMes(){
        
        double resultado = capitalAtual - capitalInicialMes;
        resultado += totalDeIRRFMes;
        
        //Se houve lucro
        if (resultado > 0){
            //Encontra o valor do IRRF do mês
            double iRRF = resultado * 0.20;
            totalDeIRRF += iRRF;
            //Desconta o IRRF do período do capital
            capitalAtual -= iRRF; 
        }
        capitalInicialMes = capitalAtual;
        totalDeIRRFMes = 0d;
    }
    
    
    //Movimenta os valores da carteira
    public void movimentaCarteira(double valorComprado, double valorVendido){
        capitalAtual = capitalAtual - valorComprado + valorVendido;
        double resultado =  valorVendido - valorComprado;
        //Se houve lucro
        if (resultado > 0){
            double iRRF = resultado * 0.01;
            capitalAtual = capitalAtual - iRRF;
            totalDeIRRFMes = totalDeIRRFMes + iRRF;
            operaçõesComLucro++;
        } else {
            operaçõesComPrejuízo++;
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

    public double getTotalDeIRRF() {
        return totalDeIRRF;
    }

    public double getTotalDeCorretagem() {
        return totalDeCorretagem;
    }

    public int getOperaçõesComLucro() {
        return operaçõesComLucro;
    }

    public int getOperaçõesComPrejuízo() {
        return operaçõesComPrejuízo;
    }
    
    

}
