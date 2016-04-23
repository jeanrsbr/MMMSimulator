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

}
