package aed.tables;

import java.util.function.Function;

public class main {

    // https://www.geeksforgeeks.org/java/generate-random-string-of-given-size-in-java/
    public static String getAlphaNumericString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {

            int index = (int)(AlphaNumericString.length() * Math.random());

            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    public static void main(String[] args){

        //Retirada daqui -> https://labex.io/tutorials/java-how-to-generate-hash-codes-for-integers-in-java-414034
        Function<Integer, Integer> f = x -> (31 * x) ^ (x >>> 16);
        Function<String, Integer> ff = x -> 0;
        Function<String, Integer> fff = x -> x.hashCode();



        int n = 200000;

        System.out.println("\n------------ Teste de Inserção - String Randoms  ----------------\n");

        for (int i = 1; i < 11; i++){
            UAlshTable<String, Integer> table = new UAlshTable<>(ff);
            for (int j = 0; j < 1000000; j++){
                table.put(getAlphaNumericString(10), j);
            }

            int c = table.getComparacoes();
            System.out.println("i = " + i + " -- número de comparações: " + c + " -- média: " + (float)c/1000000);
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

        System.out.println("\n------------ Teste de Pesquisa Existente - Usando String e hc2 mau ----------------\n");

        for (int i = 1; i < 11; i++){
            UAlshTable<String , Integer> table = new UAlshTable<>(ff);
            String[] chaves = new String[1000000];
            for (int j = 0; j < 1000000; j++){
                String c = getAlphaNumericString(10);
                chaves[j] = c;
                table.put(c, j);
            }
            table.resetComparacoes();

            for (int j = 0; j < 1000000; j++){
                table.get(chaves[(int) (Math.random() * 1000000)]);
            }

            int c = table.getComparacoes();
            System.out.println("i = " + i + " -- número de comparações: " + c + " -- média: " + (float)c/1000000);
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
