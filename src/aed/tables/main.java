package aed.tables;

import java.util.function.Function;

public class main {

    //Retirada daqui -> https://labex.io/tutorials/java-how-to-generate-hash-codes-for-integers-in-java-414034
    public static Function<Integer, Integer> f = x -> (31 * x) ^ (x >>> 16);
    public static Function<String, Integer> ff = x -> x.hashCode();


    public static void main(String[] args){

        insercaoStringRandoms();
        insercaoValoresCrescentes();
        insercaoAumentandoElementos();

        pesquisaExistenteString();
        pesquisaAumentandoElementos();
        pesquisaInexistente();
    }


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

    public static void insercaoStringRandoms(){
        System.out.println("\n------------ Teste de Inserção - String Randoms  ----------------\n");

        for (int i = 1; i < 5; i++){
            UAlshTable<String, Integer> table = new UAlshTable<>(ff);
            int j = 0;
            while(table.size() < 1000000){
                table.put(getAlphaNumericString(10), j++);
            }

            int c = table.getComparacoes();
            System.out.println("i = " + i + " -- número de comparações: " + c + " -- média: " + (float)c/1000000 + " -- atualizações: " + table.getAtualizacoes());
        }
    }

    public static void insercaoValoresCrescentes(){
        System.out.println("\n------------ Teste de Inserção - Valores crescentes  ----------------\n");

        int n = 200000;
        for (int i = 1; i < 11; i++){
            UAlshTable<Integer , Integer> table = new UAlshTable<>(f);
            for (int j = 0; j < n; j++){
                table.put(j, j);
            }

            int c = table.getComparacoes();
            System.out.println("i = " + i + " -- número de comparações: " + c + " -- média: " + (float)c/n);
        }
    }

    public static void insercaoAumentandoElementos(){
        System.out.println("\n------------ Teste de Inserção - Aumentando o número de elementos (n) ----------------\n");

        int n = 2000000;
        for (int i = 1; i < 11; i++){
            UAlshTable<Integer , Integer> table = new UAlshTable<>(f);
            for (int j = 0; j < n * i; j++){
                table.put((int) (Math.random() * 1000000000), j);
            }

            int c = table.getComparacoes();
            System.out.println("n = " + i * n + " -- número de comparações: " + c + " -- média: " + (float)c/(n*i));
        }
    }

    public static void pesquisaExistenteString(){
        System.out.println("\n------------ Teste de Pesquisa Existente - Usando String ----------------\n");

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
    }

    public static void pesquisaAumentandoElementos(){
        System.out.println("\n------------ Teste de Pesquisa Existente - Aumentando o numero de pesquisas (n) ----------------\n");

        int n = 200000;

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
    }

    public static void pesquisaInexistente(){
        System.out.println("\n------------ Teste de Pesquisa Inexistente ----------------\n");

        for (int i = 1; i < 11; i++){
            UAlshTable<String , Integer> table = new UAlshTable<>(ff);
            int n = 1000000;

            for (int j = 0; j < n; j++){
                table.put("A" + getAlphaNumericString(9), j);
            }

            table.resetComparacoes();

            for (int j = 0; j < n; j++){
                table.get("B" + getAlphaNumericString(9));
            }

            int c = table.getComparacoes();

        System.out.println("i = " + i + " -- número de comparações: " + c + " -- média: " + (float)c/1000000);
        }
    }


}
