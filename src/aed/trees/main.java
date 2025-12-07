package aed.trees;

import aed.utils.TemporalAnalysisUtils;
import java.util.function.Consumer;
import java.util.function.Function;

public class main {
    public static void main(String[] args){

        // https://docs.vultr.com/java/standard-library/java/lang/Math/random

        int min = 0;
        int max = 0;
        float r = 0;


        System.out.println("\n------------Teste Com Random Keys--------------\n");
        for (int i = 1; i < 11; i++) {
            UAlgTree<Integer, Integer> treeRandomKeys = new UAlgTree<>();
            for (int j = 0; j < 200000; j++) {
                int ran = (int) (Math.random() * 10000000);
                treeRandomKeys.put(ran, ran);
            }

            min = treeRandomKeys.bfsMin();
            max = treeRandomKeys.dfsMax();
            r = treeRandomKeys.racio(min, max);

            System.out.println(i + " -> " + "min: " + min + " max: " + max + " racio: " + r);
        }

        System.out.println();

        System.out.println("\n-----------Teste Com Keys Crescente------------\n");
        for (int i = 1; i < 11; i++) {
            UAlgTree<Integer, Integer> treeRandomKeys = new UAlgTree<>();
            for (int j = 0; j < 200000; j++) {
                treeRandomKeys.put(j, j);
            }
            min = treeRandomKeys.bfsMin();
            max = treeRandomKeys.dfsMax();
            r = treeRandomKeys.racio(min, max);

            System.out.println(i + " -> " + "min: " + min + " max: " + max + " racio: " + r);
        }

        System.out.println("\n-----------Teste Com Keys Decrescente------------\n");
        for (int i = 1; i < 11; i++) {
            UAlgTree<Integer, Integer> treeRandomKeys = new UAlgTree<>();
            for (int j = 200000; j > 0; j--) {
                treeRandomKeys.put(j, j);
            }
            min = treeRandomKeys.bfsMin();
            max = treeRandomKeys.dfsMax();
            r = treeRandomKeys.racio(min, max);

            System.out.println(i + " -> " + "min: " + min + " max: " + max + " racio: " + r);
        }


        System.out.println("\n-----------Teste de razão Dobrada ------------\n");

        Function<Integer, UAlgTree<Integer, Integer>> FRD = (n) -> {
            UAlgTree<Integer, Integer> treeRD = new UAlgTree<>();
            for (int i = 0; i < n; i++){
                treeRD.put(i, i);
            }

            return treeRD;
        };

        Consumer<UAlgTree<Integer, Integer>> CRD = (t) ->{

            int n = t.size();
            for (int i = 0; i < n; i++){
                t.get((int) (Math.random()*n));
            }
        };

        TemporalAnalysisUtils.runDoublingRatioTest(FRD, CRD, 17);



        System.out.println("\n-----------Teste de razão Dobrada 80/20 (Regra de Pareto) ------------\n");

        Function<Integer, UAlgTree<Integer, Integer>> FPareto = (n) -> {
            UAlgTree<Integer, Integer> treePareto = new UAlgTree<>();
            for (int i = 0; i < n; i++){
                treePareto.put(i, i);
            }

            return treePareto;
        };

        Consumer<UAlgTree<Integer, Integer>> CPareto = (t) ->{
            int n = t.size();
            int maisPesquisado =(int) (n * 0.20);


            for (int i = 0; i < n; i++){
                int o = (int) (Math.random() * n);

                if (o > maisPesquisado){
                    t.get((int) (Math.random()*maisPesquisado));
                    continue;
                }
                int rangeRandom = maisPesquisado + (int)(Math.random() * ((n - maisPesquisado)));
                t.get(rangeRandom);
            }
        };

        TemporalAnalysisUtils.runDoublingRatioTest(FPareto, CPareto, 17);


















    }
}
