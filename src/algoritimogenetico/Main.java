/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritimogenetico;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Fernando
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileReader(Main.class.getResource("1.txt").getPath()));
        int nVertices = scan.nextShort();
        short nMedianas = scan.nextShort();
        Gene populacao[] = new Gene[nVertices];

        for (int i = 0; i < nVertices; i++) {
            int posicaoX = scan.nextInt();
            int posicaoY = scan.nextInt();
            short capacidade = scan.nextShort();
            short demanda = scan.nextShort();
            populacao[i] = new Gene(posicaoX, posicaoY, capacidade, demanda);
        }
        Cromossomo cromossomo = new Cromossomo(populacao, nMedianas);
        scan.close();
        System.out.println(cromossomo.populacao.length);
        cromossomo.encontraPrimeirasMedianas();
        cromossomo.iniciaPrimeirasMedianas();

    }

    static class Cromossomo {

        private Gene populacao[];
        private Gene medianas[];

        private Cromossomo(Gene[] populacao, short nMedianas) {
            this.populacao = populacao;
            this.medianas = new Gene[nMedianas];
        }

        private void encontraPrimeirasMedianas() {
            for (int i = 0; i < this.medianas.length; i++) {
                int rand = (int) Math.round(Math.random() * this.populacao.length);
                if (!this.populacao[rand].isMediana) {
                    this.populacao[rand].isMediana = true;
                    medianas[i] = this.populacao[rand];
                } else {
                    i--;
                }
            }
        }

        private void iniciaPrimeirasMedianas() {
            int genesAssociados = 0;
            int genesTotais = populacao.length - medianas.length;
            for (short i = 0; i < medianas.length; i++) {
                int j = 0;
                while (genesAssociados < genesTotais && j < populacao.length ) {
                    for (j = 0; j < populacao.length; j++) {
                        if (!populacao[j].possuiMediana && (populacao[j].demanda + medianas[i].capacidadeUsada) < medianas[i].capacidade) {
                            populacao[j].possuiMediana = true;
                            medianas[i].genesMediana.add(populacao[j]);
                            medianas[i].capacidadeUsada += populacao[j].demanda;
                            genesAssociados++;
                        }
                    }
                }
            }

            for (int i = 0; i < medianas.length; i++) {
                System.out.println(medianas[i].capacidadeUsada);
                for (int j = 0; j < medianas[i].genesMediana.size(); j++) {
                    System.out.println(medianas[i].genesMediana.get(j).toString());
                }
            }
        }

    }

    static class Gene {

        private final int posicaoX;
        private final int posicaoY;
        private final short capacidade;
        private final short demanda;
        private boolean isMediana;
        private boolean possuiMediana;
        private int capacidadeUsada;
        private List<Gene> genesMediana = new ArrayList<Gene>();

        private Gene(int posicaoX, int posicaoY, short capacidade, short demanda) {
            this.posicaoX = posicaoX;
            this.posicaoY = posicaoY;
            this.capacidade = capacidade;
            this.demanda = demanda;
        }

        public double calculaDistancia(Gene gene) {
            return Math.sqrt(Math.pow(Math.abs(this.posicaoX - gene.posicaoX), 2) + Math.pow(Math.abs(this.posicaoY - gene.posicaoY), 2));
        }

        @Override
        public String toString() {
            return "posicaoX = " + posicaoX
                    + "\t posicaoY = " + posicaoY
                    + "\t capacidade =" + capacidade
                    + "\t demanda = " + demanda;
        }

    }

}
