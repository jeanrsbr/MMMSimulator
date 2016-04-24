/*
 * MMMSimulator
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package MMMSimulator.PREDICOES;

import MMMSimulator.MISC.LeituraProperties;
import MMMSimulator.SimuladorConstants;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Possui as predições do algoritmo SVM em um array
 */
public class Predicoes {

    private HashMap<Date, TransacaoPredita> predicoes;

    public Predicoes() {
        predicoes = new HashMap<>();
        populaPredicoes();
    }

    public HashMap<Date, TransacaoPredita> getPredicoes() {
        return predicoes;
    }



    //Popula o array de predições
    private void populaPredicoes() {

        try {
            //Abre arquivo CSV
            BufferedReader br = new BufferedReader(new FileReader(SimuladorConstants.SIMULADOR_FOLDER +
                    SimuladorConstants.PREDICOES_NAME + SimuladorConstants.PREDICOES_EXT));
            //Descarta a primeira linha
            br.readLine();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

            //Varre o arquivo
            while (true) {
                String linha = br.readLine();

                if (linha == null) {
                    break;
                }

                //Obtém a linha desmontada
                String[] line = linha.split(";");

                Date date = formatter.parse(line[0]);
                int prioridade = Integer.parseInt(line[1].replaceAll(",", "."));
                String ativo = line[2];
                double stopGain = Double.parseDouble(line[3].replaceAll(",", "."));
                double stopLoss = Double.parseDouble(line[4].replaceAll(",", "."));

                if (prioridade != 1){
                    continue;
                }                
                
                //Inclui no HashMAP
                predicoes.put(date, new TransacaoPredita(prioridade, date, ativo, stopGain, stopLoss));


            }
        } catch (Exception ex) {

        }

    }
}
