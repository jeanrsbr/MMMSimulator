/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MMMSimulator.MERCADO;

import java.util.Date;

/**
 *
 * @author Jean-NoteI5
 */
public class Movimentacao {
    
    private Date date;
    private double precoCompra;
    private double precoVenda;
    private double quantidadeMovimentada;

    public Movimentacao(Date date, double quantidadeMovimentada) {
        this.date = date;
        this.quantidadeMovimentada = quantidadeMovimentada;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }


    
    
}
