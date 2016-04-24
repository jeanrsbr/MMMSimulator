/*
 * MMMSimulator
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package MMMSimulator.MERCADO;

import MMMSimulator.SimuladorConstants;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.SortedSet;

/**
 * Descrição da classe.
 */
public class Ativo {

    private String nomArq;
    private String ativo;
    private HashMap<Long, Cotacao> cotacoes;

    public Ativo(String nomArq, String ativo) throws AtivoException {
        this.nomArq = nomArq;
        this.ativo = ativo;
        cotacoes = new HashMap<>();
        populaCotacoes();
    }

    //Retorna o preço de abertura do ativo
    public double getPrecoAbertura(Date date) throws AtivoException {

        long timeStampIni = getTimeStampIni(date);
        long timeStampFim = getTimeStampFim(date);

        SortedSet<Long> chaves = (SortedSet<Long>) cotacoes.keySet();

        //Varre as chaves
        for (Long chave : chaves) {

            //Se processou data inferior ao dia 
            if (chave < timeStampIni) {
                continue;
            }

            //Se processou data posterior ao dia 
            if (chave > timeStampFim) {
                break;
            }

            //Retorna o preço de abertura da seção
            return cotacoes.get(chave).getOpen();

        }

        throw new AtivoException("Não foi possível encontrar o preço de abertura da seção");

    }

    public double getPrecoFechamento(Date date) throws AtivoException {
        long timeStampIni = getTimeStampIni(date);
        long timeStampFim = getTimeStampFim(date);

        SortedSet<Long> chaves = (SortedSet<Long>) cotacoes.keySet();

        double precoFechamento = 0d;
        
        //Varre as chaves
        for (Long chave : chaves) {
            //Se processou data inferior ao dia 
            if (chave < timeStampIni) {
                continue;
            }
            //Se processou data posterior ao dia 
            if (chave > timeStampFim) {
                break;
            }
            precoFechamento = cotacoes.get(chave).getClose();

        }
        
        return precoFechamento;
    }

    public double verificaStop(Date date, double stopLoss, double stopGain) {
        long timeStampIni = getTimeStampIni(date);
        long timeStampFim = getTimeStampFim(date);
        SortedSet<Long> chaves = (SortedSet<Long>) cotacoes.keySet();

        //Varre as chaves
        for (Long chave : chaves) {

            //Se processou data inferior ao dia 
            if (chave < timeStampIni) {
                continue;
            }

            //Se processou data posterior ao dia 
            if (chave > timeStampFim) {
                break;
            }

            //Se a cotação máxima do minuto ultrapassou o StopGain
            if (cotacoes.get(chave).getHigh() > stopGain) {
                return cotacoes.get(chave).getHigh();
            }

            //Se a cotação mínima do minuto ultrapassou o StopLoss
            if (cotacoes.get(chave).getLow() > stopLoss) {
                return cotacoes.get(chave).getLow();
            }

        }

        //Se não ultrapassou o Stop GAIN e nem o Stop LOSS
        return 0d;

    }

    //Popula as cotações do ativo
    private void populaCotacoes() throws AtivoException {

        try {

            //Abre arquivo CSV
            BufferedReader br = new BufferedReader(new FileReader(SimuladorConstants.SIMULADOR_FOLDER + nomArq));

            //Descarta a primeira linha
            br.readLine();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");

            //Varre o arquivo
            while (true) {
                String linha = br.readLine();

                if (linha == null) {
                    break;
                }

                //Obtém a linha desmontada
                String[] line = linha.split(";");

                Date date = formatter.parse(line[0]);
                Calendar c = Calendar.getInstance();
                c.setTime(date);

                //Obtém dados do arquivo CSV
                long tS = c.getTimeInMillis();
                double open = Double.parseDouble(line[1].replaceAll(",", "."));
                double high = Double.parseDouble(line[2].replaceAll(",", "."));
                double low = Double.parseDouble(line[3].replaceAll(",", "."));
                double close = Double.parseDouble(line[4].replaceAll(",", "."));

                //Cria nova cotação
                cotacoes.put(tS, new Cotacao(tS, date, open, high, low, close));

            }
        } catch (IOException | ParseException | NumberFormatException ex) {
            throw new AtivoException("Não foi possível importar o arquivo com as cotações do ativo");
        }

    }

    private long getTimeStampIni(Date date) {
        //Obtém o timeStamp da hora 00:00
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    private long getTimeStampFim(Date date) {

        //Obtém o timeStamp da hora 23:59
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        //Seta o timeStamp para o final do dia
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        return c.getTimeInMillis();

    }

}
