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

        Function<String, Integer> f = x -> x.hashCode();

        UAlshTable<String , Integer> Table = new UAlshTable<>(f);
/*
        Table.put(242165, 523);
        Table.put(2*242165, 30243);
        Table.put(3*242165, 40423);
        Table.put(4*242165, 50243);
        Table.put(5*242165, 604234);
        Table.put(6*242165, 73224);
        Table.put(259, 744);


 */

        String[] teste = {"kfM1Scs", "xe7LvVc", "BjeWFZ5", "AaBBBBBB",
        "0ZGfmKF", "R8yil9o", "AaBBAaAa", "zdTd2eU", "8iEI26c", "kGmAcga", "OGYBx0F", "AaAaBBAa", "BBAaAaAa", "IOC0A9l", "a2cBREr", "AaBBAaBB", "BaVDIcP", "72BsWLF",
        "AaBBBBAa", "B5NBtXK", "hC8xsJj", "AaAaBBBB","HAv2ien", "BBAaAaBB","yAhaBlD",
        "wlHaB8g","llLymNY","sx0cvvi","p5tvusa","t00w3EP","0hJp3yu","5vqgTcX","AaAaAaAa","AaAaAaBB","srg30vn"};


        for (String s : teste){
            Table.put(s, 100101);
        }


        for (String i : Table.keys()){
            System.out.println(i);
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
