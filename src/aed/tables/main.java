package aed.tables;

import com.sun.jdi.Value;

import java.security.Key;
import java.util.Arrays;
import java.util.function.Function;

public class main {

    /*
    Chaves com colisão

    37, 74, 111, 148, 185, 222, 259, 296, 333, 370
     */


    public static void main(String[] args){

        Function<Integer, Integer> f = x -> x;

        UAlshTable<Integer, Integer> Table = new UAlshTable<>(f);

        Table.put(242165, 523);
        Table.put(2*242165, 30243);
        Table.put(3*242165, 40423);
        Table.put(4*242165, 50243);
        Table.put(5*242165, 604234);
        Table.put(6*242165, 73224);
        Table.put(259, 744);

        System.out.println(Arrays.toString(Table.getSubTable(1)));


        for (IUAlshBucket<Integer, Integer> i : Table.getSubTable(1)){
            if(i == null) continue;
            System.out.println(i.getKey());
        }



        /*

        System.out.println("Size: ");
        System.out.println(Table.size());
        System.out.println("Deletados: ");
        System.out.println(Table.getDeletedNotRemoved());
        System.out.println("LoadFactory: ");
        System.out.println(Table.getLoadFactor());


         */




    }
}
