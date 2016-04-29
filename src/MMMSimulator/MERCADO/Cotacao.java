/*
 * MMMSimulator
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package MMMSimulator.MERCADO;

import java.util.Date;

/**
 * Descrição da classe.
 */
public class Cotacao {

    private final long timeStamp;
    private final Date date;
    private final double open;
    private final double high;
    private final double low;
    private final double close;

    public Cotacao(long timeStamp, Date date, double open, double high, double low, double close) {
        this.timeStamp = timeStamp;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }
}
