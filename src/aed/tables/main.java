package aed.tables;

import java.util.function.Function;

public class main {


    public static void main(String[] args){

        //Ideia retirada daqui -> https://labex.io/tutorials/java-how-to-generate-hash-codes-for-integers-in-java-414034
        Function<Integer, Integer> f = x -> (31 * x) ^ (x >>> 16);
        Function<Integer, Integer> ff = x -> x * x;

        int n = 200000;

        System.out.println("\n------------ Teste de Inserção - Valores Random  ----------------\n");

        for (int i = 1; i < 11; i++){
            UAlshTable<Integer , Integer> table = new UAlshTable<>(f);
            for (int j = 0; j < n; j++){
                table.put((int) (Math.random() * 1000000000), j);
            }

            int c = table.getComparacoes();
            System.out.println("i = " + i + " -- número de comparações: " + c + " -- média: " + (float)c/n);
        }

        System.out.println("\n------------ Teste de Inserção - Valores crescentes  ----------------\n");

        for (int i = 1; i < 11; i++){
            UAlshTable<Integer , Integer> table = new UAlshTable<>(f);
            for (int j = 0; j < n; j++){
                table.put(j, j);
            }

            int c = table.getComparacoes();
            System.out.println("i = " + i + " -- número de comparações: " + c + " -- média: " + (float)c/n);
        }

        System.out.println("\n------------ Teste de Inserção - Aumentando o número de elementos (n) ----------------\n");

        for (int i = 1; i < 11; i++){
            UAlshTable<Integer , Integer> table = new UAlshTable<>(f);
            for (int j = 0; j < n * i; j++){
                table.put((int) (Math.random() * 1000000000), j);
            }

            int c = table.getComparacoes();
            System.out.println("n = " + i * n + " -- número de comparações: " + c + " -- média: " + (float)c/(n*i));
        }

        System.out.println("\n------------ Teste de Pesquisa Existente ----------------\n");

        for (int i = 1; i < 11; i++){
            UAlshTable<Integer , Integer> table = new UAlshTable<>(f);
            for (int j = 0; j < n; j++){
                table.put(j, j);
            }
            table.resetComparacoes();

            for (int j = 0; j < n; j++){
                table.get((int) (Math.random() * n));
            }

            int c = table.getComparacoes();
            System.out.println("i = " + i + " -- número de comparações: " + c + " -- média: " + (float)c/n);
        }

        System.out.println("\n------------ Teste de Pesquisa Existente - Aumentando o numero de pesquisas (n) ----------------\n");

        for (int i = 1; i < 11; i++){
            UAlshTable<Integer , Integer> table = new UAlshTable<>(f);
            for (int j = 0; j < n; j++){
                table.put(j, j);
            }
            table.resetComparacoes();

            for (int j = 0; j < n * i; j++){
                table.get((int) (Math.random() * n));
            }

            int c = table.getComparacoes();
            System.out.println("n = " + n * i + " -- número de comparações: " + c + " -- média: " + (float)c/(n*i));
        }


        System.out.println("\n------------ Teste de Pesquisa Inexistente ----------------\n");

        for (int i = 1; i < 11; i++){
            UAlshTable<Integer , Integer> table = new UAlshTable<>(f);
            for (int j = 0; j < n; j++){
                table.put(j, j);
            }
            table.resetComparacoes();

            for (int j = 0; j < n; j++){
                int rangeRandom = n + (int)(Math.random() * ((100000000 - n)));
                table.get(rangeRandom);
            }

            int c = table.getComparacoes();
            System.out.println("i = " + i + " -- número de comparações: " + c + " -- média: " + (float)c/n);
        }
    }
}
