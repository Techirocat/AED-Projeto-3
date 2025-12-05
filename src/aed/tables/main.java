package aed.tables;

import java.util.function.Function;

public class main {

    /*
    Chaves com colisão

    37, 74, 111, 148, 185, 222, 259, 296, 333, 370
     */


    public static void main(String[] args){

        Function<Integer, Integer> f = x -> x;

        UAlshTable<Integer, Integer> Table = new UAlshTable<>(f);

        Table.put(0, 523);
        Table.put(37, 30243);
        Table.put(74, 40423);
        Table.put(148, 50243);
        Table.put(111, 604234);
        Table.put(222, 73224);
        Table.put(259, 744);


        System.out.println(Table.get(0));
        System.out.println(Table.get(37));
        System.out.println(Table.get(74));
        System.out.println(Table.get(148));
        System.out.println(Table.get(111));
        System.out.println(Table.get(222));
        System.out.println(Table.get(259));




        System.out.println("Size: ");
        System.out.println(Table.size());
        System.out.println("Deletados: ");
        System.out.println(Table.getDeletedNotRemoved());
        System.out.println("LoadFactory: ");
        System.out.println(Table.getLoadFactor());





    }
}
